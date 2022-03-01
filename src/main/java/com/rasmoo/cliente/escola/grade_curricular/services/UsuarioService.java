package com.rasmoo.cliente.escola.grade_curricular.services;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.EmailExistenteException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.UserNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.UsuarioDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Credencial;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Usuario;
import com.rasmoo.cliente.escola.grade_curricular.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    private PasswordEncoder pass;

    public MessageResponseDTO createUsuario(UsuarioDTO usuarioDTO) throws EmailExistenteException {

        if (isEmailRepetido(usuarioDTO.getEmail())) {
            throw new EmailExistenteException(usuarioDTO.getEmail());
        }

        Usuario usuario = usuarioRepository.save(this.getUsuarioFromDTO(usuarioDTO));

        return createdMessageResponse(usuario.getId(), "Usuario criado com ID ");
    }

    public Optional<Usuario> findUsuarioPorEmail(String email) {
        Optional<Usuario> user = usuarioRepository.findByCredencial_Email(email);
        return user;
    }

    public String alterarSenhaUsuario(UsuarioDTO usuarioDTO) throws UserNotFoundException {

        Optional<Usuario> userOptional = findUsuarioPorEmail(usuarioDTO.getEmail());

        if (!userOptional.isPresent()) {
            throw new UserNotFoundException(usuarioDTO.getEmail());
        }

        Usuario user = userOptional.get();
        user.getCredencial().setSenha(pass.encode(usuarioDTO.getSenha()));

        return "Senha alterada para o usuario " + user.getCredencial().getEmail();
    }

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

    private MessageResponseDTO createdMessageResponse(Long id, String message) {

        return MessageResponseDTO
                .builder()
                .id(id)
                .message(message + id)
                .build();

    }

    private Boolean isEmailRepetido(String email) {
        Optional<Usuario> user = findUsuarioPorEmail(email);
        return user.isPresent();
    }

}
