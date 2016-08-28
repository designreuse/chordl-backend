package com.robotnec.chords.persistence.repository;

import com.robotnec.chords.persistence.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
}