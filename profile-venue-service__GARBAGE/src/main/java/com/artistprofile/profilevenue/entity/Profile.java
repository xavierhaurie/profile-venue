package com.artistprofile.profilevenue.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "email")
    String email;
    @Column(name = "notes")
    String notes;
    // later, list of images
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileVenue> profileVenues = new ArrayList<>();

    public Profile() {
    }

    public Profile(String name, String email, String description, List<ProfileVenue> profileVenues) {
        this.name = name;
        this.email = email;
        this.description = description;
        this.profileVenues = profileVenues;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setNotes(String description) {
        this.notes = notes;
    }

    public List<ProfileVenue> getProfileVenues() {
        return profileVenues;
    }

    public void setProfileVenues(List<ProfileVenue> profileVenues) {
        this.profileVenues = profileVenues;
    }

}
