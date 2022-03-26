package com.rasmoo.cliente.escola.grade_curricular.controllers;

import com.rasmoo.cliente.escola.grade_curricular.config.SwaggerConfig;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.SendIdException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.UserNotAuthorizeException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.UserNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.ResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.services.MateriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = SwaggerConfig.Materia)
@RestController
@RequestMapping("/api/v1/materias")
@CrossOrigin
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MateriaController {

    private MateriaService materiaService;

    @ApiOperation(value = "Buscar todas as materias.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de entidades exibida com sucesso."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @GetMapping
    public ResponseDTO<List<MateriaDTO>> listMaterias () throws UserNotFoundException {

        ResponseDTO<List<MateriaDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setData(materiaService.listMaterias());
        responseDTO.setHttpStatus(HttpStatus.OK.value());

        return responseDTO;

    }

    @ApiOperation(value = "Buscar materia por id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entidade encontrada."),
            @ApiResponse(code = 400, message = "Materia nao encontrado."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @GetMapping("{id}")
    public ResponseDTO<MateriaDTO> getMateriaById(@PathVariable Long id)
            throws MateriaNotFoundException, UserNotFoundException, UserNotAuthorizeException {

        ResponseDTO<MateriaDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setData(materiaService.getMateriaById(id));
        responseDTO.setHttpStatus(HttpStatus.OK.value());

        return responseDTO;

    }

    @ApiOperation(value = "Cadastrar uma nova materia.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entidade criada."),
            @ApiResponse(code = 400, message = "Erro na requisiçao enviada pelo usuario."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<MessageResponseDTO> createMateria(@Valid @RequestBody MateriaDTO materiaDTO)
            throws SendIdException, UserNotFoundException {

        if(materiaDTO.getId() != null)
            throw new SendIdException("materia");

        ResponseDTO<MessageResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setData(materiaService.createMateria(materiaDTO));
        responseDTO.setHttpStatus(HttpStatus.CREATED.value());

        return responseDTO;

    }

    @ApiOperation(value = "Atualizar uma materia.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entidade alterada com sucesso."),
            @ApiResponse(code = 400, message = "Erro na requisiçao enviada pelo usuario."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @PutMapping("{id}")
    public ResponseDTO<MessageResponseDTO> update(@Valid @PathVariable Long id, @RequestBody MateriaDTO materiaDTO)
            throws UserNotFoundException, MateriaNotFoundException, UserNotAuthorizeException {

        ResponseDTO<MessageResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setData(materiaService.updateMateria(id, materiaDTO));
        responseDTO.setHttpStatus(HttpStatus.OK.value());

        return responseDTO;

    }

    @ApiOperation(value = "Deletar uma materia.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entidade deletada com sucesso."),
            @ApiResponse(code = 400, message = "Erro na requisiçao enviada pelo usuario."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @DeleteMapping("{id}")
    public ResponseDTO<MessageResponseDTO> delete(@PathVariable Long id)
            throws UserNotFoundException, MateriaNotFoundException, UserNotAuthorizeException {

        ResponseDTO<MessageResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setData(materiaService.deleteMateriaById(id));
        responseDTO.setHttpStatus(HttpStatus.OK.value());

        return responseDTO;

    }

}
