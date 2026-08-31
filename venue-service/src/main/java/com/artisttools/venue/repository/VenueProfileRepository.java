package com.artisttools.venue.repository;

import com.artisttools.venue.entity.VenueProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VenueProfileRepository extends JpaRepository<VenueProfile, Long> {
    List<VenueProfile> findByVenueId(@Param("venueId") Long venueId);
}

