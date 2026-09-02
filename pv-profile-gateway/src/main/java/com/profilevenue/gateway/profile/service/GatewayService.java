package com.profilevenue.gateway.profile.service;

import com.profilevenue.gateway.profile.dto.*;
import com.profilevenue.gateway.profile.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GatewayService {

    private final RestClient restClient;
    private final String profileServiceBaseUrl;
    private final String venueServiceBaseUrl;
    private final String imageServiceBaseUrl;

    public GatewayService(
            RestClient.Builder restClientBuilder,
            @Value("${services.profile-service.url}") String profileServiceBaseUrl,
            @Value("${services.venue-service.url}") String venueServiceBaseUrl,
            @Value("${services.image-service.url}") String imageServiceBaseUrl
    ) {
        this.restClient = restClientBuilder.build();
        this.profileServiceBaseUrl = profileServiceBaseUrl;
        this.venueServiceBaseUrl = venueServiceBaseUrl;
        this.imageServiceBaseUrl = imageServiceBaseUrl;
    }

    public ProfileResponse getProfileWithVenuesAndImageUrls(Long profileId) {
        ProfileSummary profile = restClient.get()
                .uri(profileServiceBaseUrl + "/profile/{profileId}", profileId)
                .retrieve()
                .body(ProfileSummary.class);

        if (profile == null) {
            throw new IllegalArgumentException("Profile summary not found for userId: " + profileId);
        }

        // Venues attached to this profile
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

        // images attached to this profile
        List<ProfileImageSummary> profileImages = restClient.get()
                .uri(profileServiceBaseUrl + "/profile/{profileId}/images", profile.id())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        List<ProfileImageSummary> images = profileImages.stream()
                .map(profileImage -> {
                    ImageSummary image = restClient.get()
                            .uri(imageServiceBaseUrl + "/image/{imageId}", profileImage.imageId())
                            .retrieve()
                            .body(ImageSummary.class);

                    return new ProfileImageSummary(
                            profileImage.profileId(),
                            profileImage.imageId(),
                            Optional.ofNullable(image.title()).orElseThrow(() -> new RuntimeException("image has no title")),
                            Optional.ofNullable(image.caption()).orElse(""),
                            Optional.ofNullable(image.url()).orElse(""),
                            Optional.ofNullable(profileImage.notes()).orElse("")
                    );
                })
                .toList();

        var profileResponse = new ProfileResponse(
                profile.id(),
                profile.name(),
                profile.email(),
                profile.description(),
                groupedVenues,
                images
        );

        return profileResponse;
    }
}