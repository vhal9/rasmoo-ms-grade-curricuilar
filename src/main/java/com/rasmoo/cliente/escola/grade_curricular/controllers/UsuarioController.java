package com.rasmoo.cliente.escola.grade_curricular.controllers;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.EmailExistenteException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.ResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.UsuarioDTO;
import com.rasmoo.cliente.escola.grade_curricular.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuarios")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioController {

    private UsuarioService usuarioService;

    @PostMapping
    public ResponseDTO salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) throws EmailExistenteException {

        ResponseDTO responseDTO = new ResponseDTO<>();

        responseDTO.setData(usuarioService.createUsuario(usuarioDTO));
        responseDTO.setHttpStatus(HttpStatus.CREATED.value());

        return responseDTO;
    }
}
