package com.rasmoo.cliente.escola.grade_curricular.services;

import com.rasmoo.cliente.escola.grade_curricular.core.impl.ResourceOwner;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserInfoService implements UserDetailsService {

    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Usuario> optionalUsuario = usuarioService.findUsuarioPorEmail(email);

        Usuario user = optionalUsuario.orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado"));

        return new ResourceOwner(user);

    }
}
