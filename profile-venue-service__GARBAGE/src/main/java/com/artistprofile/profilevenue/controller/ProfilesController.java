package com.artistprofile.profilevenue.controller;

import com.artistprofile.profilevenue.dto.ProfileDTO;
import com.artistprofile.profilevenue.dto.ProfileVenueDTO;
import com.artistprofile.profilevenue.dto.VenueWithNotesDTO;
import com.artistprofile.profilevenue.entity.Profile;
import com.artistprofile.profilevenue.entity.ProfileVenue;
import com.artistprofile.profilevenue.entity.Venue;
import com.artistprofile.profilevenue.exception.ProfileNotFoundException;
import com.artistprofile.profilevenue.repository.ProfileRepository;
import com.artistprofile.profilevenue.repository.ProfileVenueRepository;
import com.artistprofile.profilevenue.repository.VenueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProfilesController {

    private final ProfileRepository profileRepository;
    private final VenueRepository venueRepository;
    private final ProfileVenueRepository profileVenueRepository;
    public ProfilesController(ProfileRepository profileRepository,
                              VenueRepository venueRepository,
                              ProfileVenueRepository profileVenueRepository) {
        this.profileRepository = profileRepository;
        this.profileVenueRepository = profileVenueRepository;
        this.venueRepository = venueRepository;
    }

    private Logger logger = LoggerFactory.getLogger(ProfilesController.class);

    @GetMapping("/profiles")
    public List<ProfileDTO> getAllProfiles(
    ) {
        logger.info("getAllProfiles() called");

        List<ProfileDTO> profiles = profileRepository.findAll()
                        .stream()
                        .map(ProfileDTO::from)
                        .toList();

        logger.info("getAllProfiles() is returning {} records", profiles.size());

        return profiles;
    }

    @GetMapping("/profile/{profileId}")
    public ProfileDTO getProfile(@PathVariable Long profileId) {
        logger.info("getProfile() called with profile_id {}", profileId);
        ProfileDTO profileDTO = profileRepository.findById(profileId)
                .map(ProfileDTO::from)
                .orElseThrow(() -> new ProfileNotFoundException(profileId));

        return profileDTO;
    }

    @GetMapping("/profile/{profileId}/venues")
    public List<VenueWithNotesDTO> getProfileVenues(@PathVariable Long profileId) {
        logger.info("getProfileVenues() called with profile_id {}", profileId);
        List<VenueWithNotesDTO> venues = profileVenueRepository.findWithVenueByProfileId(profileId)
                .stream()
                .map( VenueWithNotesDTO::from)
                .toList();
        logger.info("getProfileVenues({}) is returning {} records", profileId, venues.size());

        return venues;
    }

    @PostMapping("/profile/create")
    public ProfileDTO createProfile(@RequestBody ProfileDTO profileDTO) {
        logger.info("createProfile() called with profileDTO {}", profileDTO);
        Profile profile = profileRepository.save(profileDTO.toEntity());
        return ProfileDTO.from(profile);
    }

    @PostMapping("/profile/venue/create")
    public ProfileDTO createProfileVenue(@RequestBody ProfileVenueDTO profileVenueDTO) {
        logger.info("createProfileVenue() called with profileVenueDTO {}", profileVenueDTO);
        Profile profile = profileRepository.getReferenceById(profileVenueDTO.profileId());
        Venue venue = venueRepository.getReferenceById(profileVenueDTO.venueId());
        ProfileVenue pv = profileVenueRepository.save(profileVenueDTO.toEntity(profile, venue));
        return ProfileDTO.from(profile);
    }

}
