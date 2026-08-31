package com.artisttools.venue.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "venue_profile")
public class VenueProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "notes")
    private String notes;

    public Long getId() {
        return id;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

