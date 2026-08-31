package com.artisttools.gateway.profile.service;

import com.artisttools.gateway.profile.dto.ProfileResponse;
import com.artisttools.gateway.profile.dto.ProfileSummary;
import com.artisttools.gateway.profile.dto.VenueSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.artisttools.gateway.profile.dto.ProfileVenueSummary;
import java.util.List;

@Service
public class GatewayService {

    private final RestClient restClient;
    private final String profileServiceBaseUrl;
    private final String venueServiceBaseUrl;

    public GatewayService(
            RestClient.Builder restClientBuilder,
            @Value("${services.profile-service.url}") String profileServiceBaseUrl,
            @Value("${services.venue-service.url}") String venueServiceBaseUrl
    ) {
        this.restClient = restClientBuilder.build();
        this.profileServiceBaseUrl = profileServiceBaseUrl;
        this.venueServiceBaseUrl = venueServiceBaseUrl;
    }

    public ProfileResponse getProfile(Long profileId) {
        ProfileSummary profile = restClient.get()
                .uri(profileServiceBaseUrl + "/profile/{profileId}", profileId)
                .retrieve()
                .body(ProfileSummary.class);

        if (profile == null) {
            throw new IllegalArgumentException("Profile summary not found for userId: " + profileId);
        }

        List<ProfileVenueSummary> profileVenues = restClient.get()
                .uri(profileServiceBaseUrl + "/profile/{profileId}/venues", profile.id())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        List<ProfileVenueSummary> venues = profileVenues.stream()
                .map(profileVenue -> {
                    VenueSummary venue = restClient.get()
                            .uri(venueServiceBaseUrl + "/venue/{venueId}", profileVenue.venueId())
                            .retrieve()
                            .body(VenueSummary.class);

                    return new ProfileVenueSummary(
                            profileVenue.profileId(),
                            profileVenue.venueId(),
                            venue.name(),
                            venue.email(),
                            venue.description(),   // or venue.venueNotes() if you keep the old name
                            profileVenue.notes()
                    );
                })
                .toList();

        var profileResponse = new ProfileResponse(
                profile.id(),
                profile.name(),
                profile.email(),
                profile.description(),
                venues
        );
        return profileResponse;
    }
}