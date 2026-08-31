package com.artistprofile.profilevenue.dto;

import com.artistprofile.profilevenue.entity.Venue;

public record VenueDTO(Long id, String name, String email, String venueNotes) {

    public static VenueDTO from(Venue v) {
        return new VenueDTO(v.getId(), v.getName(), v.getEmail(), v.getNotes());
    }

    public Venue toEntity() {
        Venue venue = new Venue();
        venue.setName(this.name);
        venue.setEmail(this.email);
        venue.setNotes(this.venueNotes);
        // Map other fields if necessary
        return venue;
    }
}