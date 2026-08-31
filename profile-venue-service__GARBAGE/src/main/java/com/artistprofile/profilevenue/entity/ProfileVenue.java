package com.artistprofile.profilevenue.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "profile_venue")
public class ProfileVenue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @Column(name = "notes")
    private String interactionNotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

