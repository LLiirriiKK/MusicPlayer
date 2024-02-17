package ru.msc.security;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.msc.model.ERole;
import ru.msc.model.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDetailsImpl implements UserDetails {

    private final Integer id;

    private final String email;
    private final String login;
    private final String password;
    private final Set<GrantedAuthority> authorities;

    public UserDetailsImpl(Integer id, String email, String login, String password, Set<GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        Set<GrantedAuthority> authorityList = new HashSet<>();
        authorityList.add(new SimpleGrantedAuthority(user.getERole().name()));
        return new UserDetailsImpl(user.getId(), user.getEmail(), user.getLogin(), user.getPassword(), authorityList);
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
