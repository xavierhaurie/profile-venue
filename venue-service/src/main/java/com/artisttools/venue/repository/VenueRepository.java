package com.artisttools.venue.repository;

import com.artisttools.venue.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}

