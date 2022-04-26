package com.gmail.burinigor7.apigatewayservice.sercurity.jwt;

import com.gmail.burinigor7.apigatewayservice.dto.FetchedCredentials;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class JwtUser implements UserDetails {
    private static final String ROLE_PREFIX = "ROLE_";

    private final FetchedCredentials credentials;

    public JwtUser(FetchedCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(mapRoleToGrantedAuthority(credentials.getRole()));
    }

    @Override
    public String getPassword() {
        return credentials.getPassword();
    }

    @Override
    public String getUsername() {
        return credentials.getUsername();
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

    public FetchedCredentials getCredentials() {
        return credentials;
    }

    private GrantedAuthority mapRoleToGrantedAuthority(Role role) {
        return new SimpleGrantedAuthority(ROLE_PREFIX + role.toString());
    }
}
