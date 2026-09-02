package com.artisttools.profile.controller;

import com.artisttools.profile.dto.ProfileDTO;
import com.artisttools.profile.dto.ProfileImageDTO;
import com.artisttools.profile.dto.ProfileVenueDTO;
import com.artisttools.profile.entity.Profile;
import com.artisttools.profile.entity.ProfileVenue;
import com.artisttools.profile.exception.ProfileNotFoundException;
import com.artisttools.profile.repository.ProfileImageRepository;
import com.artisttools.profile.repository.ProfileRepository;
import com.artisttools.profile.repository.ProfileVenueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProfilesController {

    private final ProfileRepository profileRepository;
    private final ProfileVenueRepository profileVenueRepository;
    private final ProfileImageRepository profileImageRepository;

    public ProfilesController(ProfileRepository profileRepository,
                              ProfileVenueRepository profileVenueRepository,
                              ProfileImageRepository profileImageRepository
    ) {
        this.profileRepository = profileRepository;
        this.profileVenueRepository = profileVenueRepository;
        this.profileImageRepository = profileImageRepository;
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
    public List<ProfileVenueDTO> getProfileVenues(@PathVariable Long profileId) {
        logger.info("getProfileVenues() called with profile_id {}", profileId);
        List<ProfileVenueDTO> venues = profileVenueRepository.findByProfileId(profileId)
                .stream()
                .map( ProfileVenueDTO::from)
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

    // CALL TO REPOSITORY etc. NEEDS TO BE IN A SERVICE CLASS
    @PostMapping("/profile/venue/create")
    public ProfileVenueDTO createProfileVenue(@RequestBody ProfileVenueDTO profileVenueDTO) {
        logger.info("createProfileVenue() called with profileVenueDTO {}", profileVenueDTO);
        Profile profile = profileRepository.findById(profileVenueDTO.profileId())
                .orElseThrow(() -> new ProfileNotFoundException(profileVenueDTO.profileId()));
        ProfileVenue pv = profileVenueRepository.save(
                profileVenueDTO.toEntity(profile, profileVenueDTO.venueId()));
        return ProfileVenueDTO.from(pv);
    }

    // CALL TO REPOSITORY etc. NEEDS TO BE IN A SERVICE CLASS
    @GetMapping("/profile/{profileId}/images")
    public List<ProfileImageDTO> getProfileImages(@PathVariable Long profileId) {
        logger.info("getProfileImages() called with profile_id {}", profileId);
        List<ProfileImageDTO> images = profileImageRepository.findByProfileId(profileId)
                .stream()
                .map( ProfileImageDTO::from)
                .toList();
        logger.info("getProfileImages({}) is returning {} records", profileId, images.size());

        return images;
    }
}
