package com.artisttools.gateway.profile.service;

import com.artisttools.gateway.profile.dto.ProfileResponse;
import com.artisttools.gateway.profile.dto.ProfileSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class GatewayService {

    private final RestClient restClient;
    private final String profileServiceBaseUrl;

    public GatewayService(
            RestClient.Builder restClientBuilder,
            @Value("${services.profile-service.url}") String profileServiceBaseUrl
    ) {
        this.restClient = restClientBuilder.build();
        this.profileServiceBaseUrl = profileServiceBaseUrl;
    }

    public ProfileResponse getProfile(Long profileId) {
        ProfileSummary profile = restClient.get()
                .uri(profileServiceBaseUrl + "/profile/{profileId}", profileId)
                .retrieve()
                .body(ProfileSummary.class);

        if (profile == null) {
            throw new IllegalArgumentException("Profile summary not found for userId: " + profileId);
        }

//        List<ImageSummary> images = restClient.get()
//                .uri(imageServiceBaseUrl + "/images/profile/{profileId}", profile.id())
//                .retrieve()
//                .body(new ParameterizedTypeReference<>() {});

        List<com.artisttools.gateway.profile.dto.VenueSummary> venues = restClient.get()
                .uri(profileServiceBaseUrl + "/profile/{profileId}/venues", profile.id())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return new ProfileResponse(
                profile.id(),
                profile.name(),
                profile.email(),
                profile.description(),
                venues == null ? List.of() : venues
        );
    }
}