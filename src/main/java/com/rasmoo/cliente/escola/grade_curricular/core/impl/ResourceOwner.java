package com.rasmoo.cliente.escola.grade_curricular.core.impl;

import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ResourceOwner implements UserDetails {

    private Usuario usuario;

    public ResourceOwner(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(this.usuario.getRole()));
        return roles;
    }

    @Override
    public String getPassword() {
        return this.usuario.getCredencial().getSenha();
    }

    @Override
    public String getUsername() {
        return this.usuario.getCredencial().getEmail();
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
