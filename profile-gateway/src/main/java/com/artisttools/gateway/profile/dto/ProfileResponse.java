package com.artisttools.gateway.profile.dto;

import com.artisttools.gateway.profile.dto.VenueSummary;

import java.util.List;

public record ProfileResponse(
        Long profileId,
        String name,
        String email,
        String description,
        List<VenueSummary> venues
) {
}