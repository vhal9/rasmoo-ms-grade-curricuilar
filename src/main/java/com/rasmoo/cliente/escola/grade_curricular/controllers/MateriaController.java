package com.rasmoo.cliente.escola.grade_curricular.controllers;

import com.rasmoo.cliente.escola.grade_curricular.config.SwaggerConfig;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.SendIdException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.services.MateriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = SwaggerConfig.Materia)
@RestController
@RequestMapping("/api/v1/materias")
@CrossOrigin
@RequiredArgsConstructor
public class MateriaController {

    private final MateriaService materiaService;

    @ApiOperation(value = "Buscar todas as materias.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de entidades exibida com sucesso."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @GetMapping
    public ResponseEntity<List<MateriaDTO>> listMaterias () {

        return new ResponseEntity<>(materiaService.listMaterias(), HttpStatus.OK);

    }

    @ApiOperation(value = "Buscar materia por id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entidade encontrada."),
            @ApiResponse(code = 400, message = "Materia nao encontrado."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @GetMapping("{id}")
    public ResponseEntity<MateriaDTO> getMateriaById(@PathVariable Long id)
            throws MateriaNotFoundException {

        return new ResponseEntity<>(materiaService.getMateriaById(id), HttpStatus.OK);

    }

    @ApiOperation(value = "Cadastrar uma nova materia.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entidade criada."),
            @ApiResponse(code = 400, message = "Erro na requisiçao enviada pelo usuario."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageResponseDTO> createMateria(@Valid @RequestBody MateriaDTO materiaDTO)
            throws SendIdException {

        return new ResponseEntity<>(materiaService.createMateria(materiaDTO), HttpStatus.CREATED);

    }

    @ApiOperation(value = "Atualizar uma materia.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entidade alterada com sucesso."),
            @ApiResponse(code = 400, message = "Erro na requisiçao enviada pelo usuario."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @PutMapping("{id}")
    public ResponseEntity<MessageResponseDTO> update(@PathVariable Long id, @Valid @RequestBody MateriaDTO materiaDTO)
            throws MateriaNotFoundException {

        return new ResponseEntity<>(materiaService.updateMateria(id, materiaDTO),
                HttpStatus.OK);

    }

    @ApiOperation(value = "Deletar uma materia.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entidade deletada com sucesso."),
            @ApiResponse(code = 400, message = "Erro na requisiçao enviada pelo usuario."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponseDTO> delete(@PathVariable Long id)
            throws MateriaNotFoundException {

        return new ResponseEntity<>(materiaService.deleteMateriaById(id), HttpStatus.OK);

    }

}
