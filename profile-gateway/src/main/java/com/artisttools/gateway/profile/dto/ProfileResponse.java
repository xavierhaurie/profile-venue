package com.artisttools.gateway.profile.dto;

import java.util.List;
import java.util.Map;

public record ProfileResponse(
        Long profileId,
        String name,
        String email,
        String description,
        Map<Long, List<ProfileVenueSummary>> venues,
        List<ProfileImageSummary> images
) {
}