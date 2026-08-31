package com.artisttools.profile.repository;

import com.artisttools.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}

