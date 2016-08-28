package com.robotnec.chords.service;


import com.robotnec.chords.persistence.entity.user.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}