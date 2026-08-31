package com.artistprofile.profilevenue.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "venue_profile")
public class VenueProfile {

    @EmbeddedId
    private ProfileVenueId id;

    @ManyToOne
    @MapsId("venueId")
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @ManyToOne
    @MapsId("profileId")               // maps this FK to part of the composite key
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column(name = "notes")
    private String interactionNotes;

    public ProfileVenueId getId() {
        return id;
    }

    public void setId(ProfileVenueId id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getInteractionNotes() {
        return interactionNotes;
    }

    public void setInteractionNotes(String notes) {
        this.interactionNotes = notes;
    }
}

