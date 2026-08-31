package com.artisttools.profile.repository;

import com.artisttools.profile.entity.ProfileVenue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileVenueRepository extends JpaRepository<ProfileVenue, Long> {
    List<ProfileVenue> findByProfileId(Long profileId);
}
