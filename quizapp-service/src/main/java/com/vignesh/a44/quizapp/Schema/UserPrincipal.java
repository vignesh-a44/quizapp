package com.vignesh.a44.quizapp.Schema;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final UsersSchema userPrincipal;

    public UserPrincipal(UsersSchema user) {
        this.userPrincipal = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return this.userPrincipal.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userPrincipal.getUsername();
    }
}
