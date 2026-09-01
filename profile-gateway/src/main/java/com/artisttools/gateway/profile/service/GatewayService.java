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
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ProfileResponse getProfileWithVenues(Long profileId) {
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
                            Optional.ofNullable(venue.name()).orElseThrow(() -> new RuntimeException("venue has no name")),
                            Optional.ofNullable(venue.email()).orElse(""),
                            Optional.ofNullable(venue.description()).orElse(""),
                            Optional.ofNullable(profileVenue.notes()).orElse("")
                    );
                })
                .toList();

        var groupedVenues = venues.stream()
                .collect(Collectors.groupingBy(ProfileVenueSummary::venueId));

        var profileResponse = new ProfileResponse(
                profile.id(),
                profile.name(),
                profile.email(),
                profile.description(),
                groupedVenues
        );
        return profileResponse;
    }
}