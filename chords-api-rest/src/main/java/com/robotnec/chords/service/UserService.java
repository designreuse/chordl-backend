package com.robotnec.chords.service;


import com.robotnec.chords.persistence.entity.user.ChordsUser;

public interface UserService {
    void save(ChordsUser user);

    ChordsUser findByUsername(String username);
}