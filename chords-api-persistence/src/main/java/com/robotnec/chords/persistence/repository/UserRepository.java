package com.robotnec.chords.persistence.repository;

import com.robotnec.chords.persistence.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}