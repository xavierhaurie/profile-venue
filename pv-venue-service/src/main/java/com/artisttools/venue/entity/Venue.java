package com.profilevenue.venue.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Venue {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "email")
    String email;
    @Column(name = "description")
    String description;
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VenueProfile> venueProfiles = new ArrayList<>();

    public Venue() {
    }

    public Venue(String name, String email, String description, List<VenueProfile> venueProfiles) {
        this.name = name;
        this.email = email;
        this.description = description;
        this.venueProfiles = venueProfiles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<VenueProfile> getProfileVenues() {
        return venueProfiles;
    }

    public void setProfileVenues(List<VenueProfile> venueProfiles) {
        this.venueProfiles = venueProfiles;
    }
}
