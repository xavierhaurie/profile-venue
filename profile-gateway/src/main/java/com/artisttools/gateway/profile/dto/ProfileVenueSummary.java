package com.artisttools.gateway.profile.dto;

public record ProfileVenueSummary(Long profileId, Long venueId, String name, String email, String description, String notes) {
}