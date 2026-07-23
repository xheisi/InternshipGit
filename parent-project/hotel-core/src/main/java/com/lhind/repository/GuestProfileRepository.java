package com.lhind.repository;

import de.lhind.internship.mini.project.entity.GuestProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuestProfileRepository extends JpaRepository<GuestProfile, Integer> {

    Optional<GuestProfile> findByGuestId(int guestId);     // /api/guests/{guestId}/profile
}
