package com.robotnec.chords.service;

import com.robotnec.chords.persistence.entity.user.Role;

import java.util.Set;

/**
 * @author zak <zak@robotnec.com>
 */
public interface RoleService {
    Set<Role> getUserRoles();
    Set<Role> getModeratorRoles();
    Set<Role> getAdminRoles();
}
