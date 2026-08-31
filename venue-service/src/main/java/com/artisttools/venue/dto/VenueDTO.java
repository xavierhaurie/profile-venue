package com.artisttools.venue.dto;

import com.artisttools.venue.entity.Venue;

public record VenueDTO(Long id, String name, String email, String description) {

    public static VenueDTO from(Venue v) {
        return new VenueDTO(v.getId(), v.getName(), v.getEmail(), v.getDescription());
    }

    public Venue toEntity() {
        Venue venue = new Venue();
        venue.setName(this.name);
        venue.setEmail(this.email);
        venue.setDescription(this.description);
        // Map other fields if necessary
        return venue;
    }
}