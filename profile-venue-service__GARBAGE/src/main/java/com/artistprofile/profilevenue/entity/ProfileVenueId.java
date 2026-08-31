package com.artistprofile.profilevenue.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

// used in both ProfileVenue and VenueProfile entities
@Embeddable
public class ProfileVenueId implements Serializable {
    private Long profileId;
    private Long venueId;

    // equals() and hashCode() are REQUIRED for composite keys
    // no-arg constructor required
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProfileVenueId that)) return false;
        return Objects.equals(profileId, that.profileId) && Objects.equals(venueId, that.venueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileId, venueId);
    }
}
