package com.artisttools.profile.repository;

import com.artisttools.profile.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    List<ProfileImage> findByProfileId(Long profileId);
}
