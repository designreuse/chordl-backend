package com.robotnec.chords.persistence.repository;

import com.robotnec.chords.persistence.entity.user.ChordsUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<ChordsUser, Long> {
    ChordsUser findByEmail(String email);
}