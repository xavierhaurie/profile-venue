package com.artistprofile.profilevenue.dto;

import com.artistprofile.profilevenue.entity.Profile;
import com.artistprofile.profilevenue.entity.Venue;
import com.artistprofile.profilevenue.entity.VenueProfile;

public record VenueProfileDTO(Long profileId, Long venueId, String interactionNotes) {

    static public VenueProfileDTO from(VenueProfile vp) {
        return new VenueProfileDTO(
                vp.getProfile().getId(),
                vp.getVenue().getId(),
                vp.getInteractionNotes());
    }

    public VenueProfile toEntity(Profile profile, Venue venue) {
        VenueProfile vp = new VenueProfile();
        vp.setProfile(profile);
        vp.setVenue(venue);
        vp.setInteractionNotes(this.interactionNotes);
        return vp;
    }
}
