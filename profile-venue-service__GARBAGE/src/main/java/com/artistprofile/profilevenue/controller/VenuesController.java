package com.artistprofile.profilevenue.controller;

import com.artistprofile.profilevenue.entity.Profile;
import com.artistprofile.profilevenue.entity.VenueProfile;
import com.artistprofile.profilevenue.entity.Venue;
import com.artistprofile.profilevenue.exception.VenueNotFoundException;
import com.artistprofile.profilevenue.dto.ProfileDTO;
import com.artistprofile.profilevenue.dto.ProfileWithNotesDTO;
import com.artistprofile.profilevenue.dto.VenueDTO;
import com.artistprofile.profilevenue.dto.VenueProfileDTO;
import com.artistprofile.profilevenue.repository.ProfileRepository;
import com.artistprofile.profilevenue.repository.VenueProfileRepository;
import com.artistprofile.profilevenue.repository.VenueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VenuesController {

    private final VenueRepository venueRepository;
    private final VenueProfileRepository venueProfileRepository;
    private final ProfileRepository profileRepository;

    public VenuesController(VenueRepository venueRepository,
                            ProfileRepository profileRepository,
                            VenueProfileRepository venueProfileRepository) {
        this.venueRepository = venueRepository;
        this.profileRepository = profileRepository;
        this.venueProfileRepository = venueProfileRepository;
    }

    private Logger logger = LoggerFactory.getLogger(VenuesController.class);

    @GetMapping("/venues")
    public List<VenueDTO> getAllVenues(
    ) {
        logger.info("getAllVenues() called");

        List<VenueDTO> venues = venueRepository.findAll()
                        .stream()
                        .map(VenueDTO::from)
                        .toList();

        logger.info("getAllVenues() is returning {} records", venues.size());

        return venues;
    }

    @GetMapping("/venue/{venueId}")
    public VenueDTO getVenue(@PathVariable Long venueId) {
        logger.info("getVenue() called with venue_id {}", venueId);
        VenueDTO venueDTO = venueRepository.findById(venueId)
                .map(VenueDTO::from)
                .orElseThrow(() -> new VenueNotFoundException(venueId));

        return venueDTO;
    }

    @PostMapping("/venue/create")
    public VenueDTO createVenue(@RequestBody VenueDTO venueDTO) {
        logger.info("createVenue() called with venueDTO {}", venueDTO);
        Venue venue = venueRepository.save(venueDTO.toEntity());
        return VenueDTO.from(venue);
    }

    @GetMapping("/venue/{venueId}/profiles")
    public List<ProfileWithNotesDTO> getVenueProfiles(@PathVariable Long venueId) {
        logger.info("getVenueProfiles() called with venue_id {}", venueId);
        List<ProfileWithNotesDTO> profiles = venueProfileRepository.findWithProfileByVenueId(venueId)
                .stream()
                .map(ProfileWithNotesDTO::from)
                .toList();
        logger.info("getVenueProfiles({}) is returning {} records", venueId, profiles.size());

        return profiles;
    }

    @PostMapping("/venue/profile/create")
    public ProfileDTO createVenueProfile(@RequestBody VenueProfileDTO venueProfileDTO) {
        logger.info("createVenueProfile() called with venueProfileDTO {}", venueProfileDTO);
        Profile profile = this.profileRepository.getReferenceById(venueProfileDTO.profileId());
        Venue venue = venueRepository.getReferenceById(venueProfileDTO.venueId());
        VenueProfile pv = venueProfileRepository.save(venueProfileDTO.toEntity(profile, venue));
        return ProfileDTO.from(profile);
    }
}
