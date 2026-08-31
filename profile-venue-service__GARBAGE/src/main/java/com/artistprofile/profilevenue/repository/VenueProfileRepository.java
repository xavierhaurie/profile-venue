package com.artistprofile.profilevenue.repository;

import com.artistprofile.profilevenue.entity.ProfileVenueId;
import com.artistprofile.profilevenue.entity.VenueProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VenueProfileRepository extends JpaRepository<VenueProfile, ProfileVenueId> {
    @Query("select vp from VenueProfile vp join fetch vp.profile where vp.venue.id = :venueId")
    List<VenueProfile> findWithProfileByVenueId(@Param("venueId") Long venueId);
}

