package com.robotnec.chords.service.impl;

import com.robotnec.chords.persistence.entity.user.Role;
import com.robotnec.chords.persistence.repository.RoleRepository;
import com.robotnec.chords.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zak <zak@robotnec.com>
 */
@Component
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly=true)
    @Override
    public Set<Role> getUserRoles() {
        return buildRoles("ROLE_USER");
    }

    @Transactional(readOnly=true)
    @Override
    public Set<Role> getModeratorRoles() {
        return buildRoles("ROLE_USER", "ROLE_MODERATOR");
    }

    @Transactional(readOnly=true)
    @Override
    public Set<Role> getAdminRoles() {
        return buildRoles("ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN");
    }

    private Set<Role> buildRoles(String... roleNames) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            roles.add(getRole(roleName));
        }
        return roles;
    }

    private Role getRole(String roleName) {
        Role userRole = roleRepository.findByName(roleName);

        if (userRole == null) {
            throw new IllegalStateException("Can't find role:" + roleName);
        }
        return userRole;
    }
}
