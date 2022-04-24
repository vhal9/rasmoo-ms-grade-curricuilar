package com.rasmoo.cliente.escola.grade_curricular.services;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.EmailExistenteException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.UserNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.CredencialUsuarioDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.UsuarioDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    MessageResponseDTO createUsuario(UsuarioDTO usuarioDTO) throws EmailExistenteException;

    Optional<Usuario> findUsuarioPorEmail(String email);

    String alterarSenhaUsuario(CredencialUsuarioDTO credencialUsuarioDTO) throws UserNotFoundException;

    List<Usuario> listarUsuarios();
}
