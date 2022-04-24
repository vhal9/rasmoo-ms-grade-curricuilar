package com.rasmoo.cliente.escola.grade_curricular.services.impl;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.EmailExistenteException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.UserNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.CredencialUsuarioDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.UsuarioDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Credencial;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Usuario;
import com.rasmoo.cliente.escola.grade_curricular.repositories.UsuarioRepository;
import com.rasmoo.cliente.escola.grade_curricular.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder pass;

    @Override
    public MessageResponseDTO createUsuario(UsuarioDTO usuarioDTO) throws EmailExistenteException {

        if (isEmailRepetido(usuarioDTO.getEmail())) {
            throw new EmailExistenteException(usuarioDTO.getEmail());
        }

        Usuario usuario = usuarioRepository.save(this.getUsuarioFromDTO(usuarioDTO));

        return createdMessageResponse(usuario.getId());
    }

    @Override
    public Optional<Usuario> findUsuarioPorEmail(String email) {
        return usuarioRepository.findByCredencial_Email(email);
    }

    @Override
    public String alterarSenhaUsuario(CredencialUsuarioDTO credencialUsuarioDTO) throws UserNotFoundException {

        Optional<Usuario> userOptional = findUsuarioPorEmail(credencialUsuarioDTO.getEmail());

        if (!userOptional.isPresent()) {
            throw new UserNotFoundException(credencialUsuarioDTO.getEmail());
        }

        Usuario user = userOptional.get();
        user.getCredencial().setSenha(pass.encode(credencialUsuarioDTO.getSenha()));

        return "Senha alterada para o usuario " + user.getCredencial().getEmail();
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    private Usuario getUsuarioFromDTO(UsuarioDTO usuarioDTO) {

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setCredencial(this.instanciaCredencial(usuarioDTO));
        return usuario;

    }

    private Credencial instanciaCredencial(UsuarioDTO usuarioDTO) {

        Credencial credencial = new Credencial();
        credencial.setEmail(usuarioDTO.getEmail());
        credencial.setSenha(pass.encode(usuarioDTO.getSenha()));
        return credencial;
    }

    private MessageResponseDTO createdMessageResponse(Long id) {

        return MessageResponseDTO
                .builder()
                .id(id)
                .message("Usuario criado com ID " + id)
                .build();

    }

    private Boolean isEmailRepetido(String email) {
        Optional<Usuario> user = findUsuarioPorEmail(email);
        return user.isPresent();
    }

}
