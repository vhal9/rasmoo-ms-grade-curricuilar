package com.rasmoo.cliente.escola.grade_curricular.controllers;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.EmailExistenteException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.UserNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.ResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.UsuarioDTO;
import com.rasmoo.cliente.escola.grade_curricular.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/usuarios")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@PreAuthorize(value = "#oauth2.hasScope('cw_nao_logado')")
public class UsuarioController {

    private UsuarioService usuarioService;

    @PostMapping
    public ResponseDTO salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) throws EmailExistenteException {

        ResponseDTO responseDTO = new ResponseDTO<>();

        responseDTO.setData(usuarioService.createUsuario(usuarioDTO));
        responseDTO.setHttpStatus(HttpStatus.CREATED.value());

        return responseDTO;
    }

    @PatchMapping
    public ResponseDTO atualizarSenha(@RequestBody UsuarioDTO usuarioDTO) throws UserNotFoundException {

        ResponseDTO responseDTO = new ResponseDTO<>();

        responseDTO.setData(usuarioService.alterarSenhaUsuario(usuarioDTO));
        responseDTO.setHttpStatus(HttpStatus.CREATED.value());

        return responseDTO;

    }
}
