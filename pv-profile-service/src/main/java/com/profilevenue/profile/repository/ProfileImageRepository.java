package com.profilevenue.profile.repository;

import com.profilevenue.profile.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    List<ProfileImage> findByProfileId(Long profileId);
}
