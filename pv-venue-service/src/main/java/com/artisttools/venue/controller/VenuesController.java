package com.profilevenue.venue.controller;

import com.profilevenue.venue.entity.VenueProfile;
import com.profilevenue.venue.entity.Venue;
import com.profilevenue.venue.exception.VenueNotFoundException;
import com.profilevenue.venue.dto.VenueDTO;
import com.profilevenue.venue.dto.VenueProfileDTO;
import com.profilevenue.venue.repository.VenueProfileRepository;
import com.profilevenue.venue.repository.VenueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VenuesController {

    private final VenueRepository venueRepository;
    private final VenueProfileRepository venueProfileRepository;

    public VenuesController(VenueRepository venueRepository,
                            VenueProfileRepository venueProfileRepository) {
        this.venueRepository = venueRepository;
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
    public List<VenueProfileDTO> getVenueProfiles(@PathVariable Long venueId) {
        logger.info("getVenueProfiles() called with venue_id {}", venueId);
        List<VenueProfileDTO> profiles = venueProfileRepository.findByVenueId(venueId)
                .stream()
                .map(VenueProfileDTO::from)
                .toList();
        logger.info("getVenueProfiles({}) is returning {} records", venueId, profiles.size());

        return profiles;
    }

    @PostMapping("/venue/profile/create")
    public VenueProfileDTO createVenueProfile(@RequestBody VenueProfileDTO venueProfileDTO) {
        logger.info("createVenueProfile() called with venueProfileDTO {}", venueProfileDTO);
        Venue venue = venueRepository.findById(venueProfileDTO.venueId())
                .orElseThrow(() -> new VenueNotFoundException(venueProfileDTO.venueId()));
        VenueProfile vp = venueProfileRepository.save(
                venueProfileDTO.toEntity(venue));
        return VenueProfileDTO.from(vp);
    }

}
