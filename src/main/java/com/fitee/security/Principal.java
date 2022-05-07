package com.fitee.security;

import com.fitee.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class Principal extends User implements UserDetails {

    private Claims claims;

    public Principal(Claims claims) {
        this.claims = claims;
        super.setId(((Number) claims.get("uid")).longValue());
        super.setEmail(claims.getSubject());
        super.setPassword(""); // no pw available from claims
//        super.setRole(claims.get("roles"));
    }

    public Principal(User user) {
        super.setId(user.getId());
        super.setEmail(user.getEmail());
        super.setPassword(user.getPassword());
        super.setRole(user.getRole());
    }

/*
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = new ArrayList<String>();
        roles.add("CUSTOMER");
        roles.add("FREELANCER");
        
        return roles.stream().collect();
    }
*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("CUSTOMER");
        SimpleGrantedAuthority authority2 = new SimpleGrantedAuthority("FREELANCER");

        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(authority);
        authorities.add(authority2);

        return Collections.arrayToList(authorities);
    }

    @Override
    public String getUsername() {
        return null;
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

    public Claims getClaims() {
        return claims;
    }
}
