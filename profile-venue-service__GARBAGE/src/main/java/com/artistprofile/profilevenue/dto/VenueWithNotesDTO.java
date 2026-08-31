package com.artistprofile.profilevenue.dto;

import com.artistprofile.profilevenue.entity.ProfileVenue;

public record VenueWithNotesDTO(String notes, VenueDTO venue) {
    public static VenueWithNotesDTO from(ProfileVenue pv) {
        return new VenueWithNotesDTO(pv.getInteractionNotes(), VenueDTO.from(pv.getVenue()));
    }
}
