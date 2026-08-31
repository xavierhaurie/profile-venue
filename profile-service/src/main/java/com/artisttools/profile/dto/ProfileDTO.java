package com.artisttools.profile.dto;

import com.artisttools.profile.entity.Profile;

public record ProfileDTO(Long id, String name, String email, String description) {

    static public ProfileDTO from(Profile p) {
        return new ProfileDTO(p.getId(), p.getName(), p.getEmail(), p.getDescription());
    }

    public Profile toEntity() {
        Profile profile = new Profile();
        profile.setName(this.name);
        profile.setEmail(this.email);
        profile.setDescription(this.description);
        // Map other fields if necessary
        return profile;
    }
}
