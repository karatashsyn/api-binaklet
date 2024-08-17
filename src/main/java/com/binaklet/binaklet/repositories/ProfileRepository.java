package com.binaklet.binaklet.repositories;

import com.binaklet.binaklet.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}