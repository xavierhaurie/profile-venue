package com.artisttools.gateway.profile.dto;

import java.util.List;

public record ProfileResponse(
        Long profileId,
        String name,
        String email,
        String description,
        List<ProfileVenueSummary> venues
) {
}