package com.artistprofile.image.exception;

public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(long imageId) {
        super("Image with id " + imageId + " not found");
    }
}
