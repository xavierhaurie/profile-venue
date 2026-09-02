package com.profilevenue.profile.dto;

import com.profilevenue.profile.entity.Profile;
import com.profilevenue.profile.entity.ProfileVenue;

public record ProfileVenueDTO(Long profileId, Long venueId, String notes) {

    static public ProfileVenueDTO from(ProfileVenue pv) {
        return new ProfileVenueDTO(
                pv.getProfile().getId(),
                pv.getVenueId(),
                pv.getNotes());
    }

    public ProfileVenue toEntity(Profile profile, Long venueId) {
        ProfileVenue pv = new ProfileVenue();
        pv.setProfile(profile);
        pv.setVenueId(venueId);
        pv.setNotes(notes);
        return pv;
    }
}
