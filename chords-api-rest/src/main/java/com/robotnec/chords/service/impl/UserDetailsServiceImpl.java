package com.robotnec.chords.service.impl;

import com.robotnec.chords.persistence.entity.user.ChordsUser;
import com.robotnec.chords.persistence.repository.UserRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ChordsUser user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
        }

        Set<GrantedAuthority> grantedAuthorities =
                user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toSet());

        return new ChordsUserWrapper(user, grantedAuthorities);
    }

    private static class ChordsUserWrapper implements UserDetails {

        private final ChordsUser user;
        private final Set<GrantedAuthority> grantedAuthorities;

        ChordsUserWrapper(ChordsUser user, Set<GrantedAuthority> grantedAuthorities) {
            this.user = user;
            this.grantedAuthorities = grantedAuthorities;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return grantedAuthorities;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public String getUsername() {
            return user.getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}