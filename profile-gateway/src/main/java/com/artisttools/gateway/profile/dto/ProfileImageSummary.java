package com.artisttools.gateway.profile.dto;

public record ProfileImageSummary(Long profileId, Long imageId, String title, String caption, String url, String notes) {
}