package com.artistprofile.profilevenue.repository;

import com.artistprofile.profilevenue.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}

