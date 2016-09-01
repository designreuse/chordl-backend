package com.robotnec.chords.service.impl;

import com.robotnec.chords.persistence.entity.user.ChordsUser;
import com.robotnec.chords.persistence.repository.UserRepository;
import com.robotnec.chords.service.RoleService;
import com.robotnec.chords.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Transactional
    @Override
    public ChordsUser save(ChordsUser user) {
        user.setRoles(roleService.getAdminRoles());
        ChordsUser saved = userRepository.save(user);
        log.debug("Saved user {}", user);
        return saved;
    }

    @Override
    public Optional<ChordsUser> findByEmail(String email) {
        log.debug("Try to find by email {}", email);

        Optional<ChordsUser> chordsUser = Optional.ofNullable(userRepository.findByEmail(email));

        log.debug("Found {}", chordsUser.get());

        return chordsUser;
    }

    @Override
    public Optional<ChordsUser> findById(long userId) {
        return Optional.ofNullable(userRepository.findOne(userId));
    }
}