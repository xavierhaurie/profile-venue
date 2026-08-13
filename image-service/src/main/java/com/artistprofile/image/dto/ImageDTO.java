package com.artistprofile.image.dto;

import com.artistprofile.image.entity.Image;

public record ImageDTO(String title, String caption, String url, Long profileId) {

    static public ImageDTO from(Image i) {
        return new ImageDTO(
                i.getTitle(),
                i.getCaption(),
                i.getUrl(),
                i.getProfileId());
    }

    public Image toEntity() {
        Image image = new Image();
        image.setTitle(this.title);
        image.setCaption(this.caption);
        image.setUrl(this.url);
        image.setProfileId(this.profileId);
        // Map other fields if necessary
        return image;
    }
}
