package com.artisttools.profile.dto;

import com.artisttools.profile.entity.Profile;
import com.artisttools.profile.entity.ProfileImage;

public record ProfileImageDTO(Long profileId, Long imageId, String notes) {

    static public ProfileImageDTO from(ProfileImage pi) {
        return new ProfileImageDTO(
                pi.getProfile().getId(),
                pi.getImageId(),
                pi.getNotes());
    }

    public ProfileImage toEntity(Profile profile, Long imageId) {
        ProfileImage pi = new ProfileImage();
        pi.setProfile(profile);
        pi.setImageId(imageId);
        pi.setNotes(notes);
        return pi;
    }
}
