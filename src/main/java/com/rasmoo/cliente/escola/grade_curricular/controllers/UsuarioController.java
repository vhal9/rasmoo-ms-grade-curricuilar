package com.rasmoo.cliente.escola.grade_curricular.controllers;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.EmailExistenteException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.UserNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.CredencialUsuarioDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.UsuarioDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Usuario;
import com.rasmoo.cliente.escola.grade_curricular.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @PreAuthorize(value = "#oauth2.hasScope('cw_logado') and hasRole('ROLE_ADM')")
    public ResponseEntity<MessageResponseDTO> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) throws EmailExistenteException {

        return new ResponseEntity<>(usuarioService.createUsuario(usuarioDTO),
                HttpStatus.CREATED);
    }

    @PatchMapping
    @PreAuthorize(value = "#oauth2.hasScope('cw_nao_logado') or #oauth2.hasScope('cw_logado')")
    public ResponseEntity<String> atualizarSenha(@RequestBody CredencialUsuarioDTO credencialUsuarioDTO) throws UserNotFoundException {

        return new ResponseEntity<>(usuarioService.alterarSenhaUsuario(credencialUsuarioDTO),
                HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize(value = "#oauth2.hasScope('cw_logado') and hasRole('ROLE_ADM')")
    public ResponseEntity<List<Usuario>> listarUsuarios() {

        return new ResponseEntity<>(usuarioService.listarUsuarios(),
                HttpStatus.OK);
    }
}
