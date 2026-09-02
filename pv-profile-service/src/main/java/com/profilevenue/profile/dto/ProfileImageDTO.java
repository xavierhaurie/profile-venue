package com.profilevenue.profile.dto;

import com.profilevenue.profile.entity.Profile;
import com.profilevenue.profile.entity.ProfileImage;

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
