package com.artisttools.gateway.profile.controller;

import com.artisttools.gateway.profile.dto.ProfileImageSummary;
import com.artisttools.gateway.profile.dto.ProfileResponse;
import com.artisttools.gateway.profile.service.GatewayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class GatewayController {

    private Logger logger = LoggerFactory.getLogger(GatewayController.class);

    private final GatewayService gatewayService;

    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @GetMapping("/profile/{profileId}")
    public ProfileResponse getProfile(@PathVariable Long profileId) {
        logger.info("getProfile() called with profileId {}", profileId);
        return gatewayService.getProfileWithVenuesAndImageUrls(profileId);
    }

}
