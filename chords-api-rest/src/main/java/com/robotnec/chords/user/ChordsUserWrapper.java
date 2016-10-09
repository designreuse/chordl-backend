package com.robotnec.chords.user;

import com.robotnec.chords.persistence.entity.user.ChordsUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @author zak <zak@robotnec.com>
 */
public class ChordsUserWrapper implements UserDetails {

    private final ChordsUser user;
    private final Set<GrantedAuthority> grantedAuthorities;

    public ChordsUserWrapper(ChordsUser user, Set<GrantedAuthority> grantedAuthorities) {
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

    public ChordsUser getChordsUser() {
        return user;
    }
}
