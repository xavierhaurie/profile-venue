package com.artistprofile.profilevenue.entity;

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
    @Column(name = "notes")
    String notes;
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileVenue> profileVenues = new ArrayList<>();

    public Venue() {
    }

    public Venue(String name, String email, String notes, List<ProfileVenue> profileVenues) {
        this.name = name;
        this.email = email;
        this.notes = notes;
        this.profileVenues = profileVenues;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProfileVenue> getProfileVenues() {
        return profileVenues;
    }

    public void setProfileVenues(List<ProfileVenue> profileVenues) {
        this.profileVenues = profileVenues;
    }
}
