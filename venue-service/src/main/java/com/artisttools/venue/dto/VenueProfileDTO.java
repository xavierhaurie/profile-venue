package com.artisttools.venue.dto;

import com.artisttools.venue.entity.Venue;
import com.artisttools.venue.entity.VenueProfile;

import java.util.List;

public record VenueProfileDTO(Long id, Long profileId, Long venueId, String notes) {

    static public VenueProfileDTO from(VenueProfile vp) {
        return new VenueProfileDTO(
                vp.getId(),
                vp.getProfileId(),
                vp.getVenue().getId(),
                vp.getNotes());
    }

    public VenueProfile toEntity(Venue venue) {
        VenueProfile vp = new VenueProfile();
        vp.setProfileId(this.profileId);
        vp.setVenue(venue);
        vp.setNotes(this.notes);
        return vp;
    }
}
