package com.rasmoo.cliente.escola.grade_curricular.controllers;

import com.rasmoo.cliente.escola.grade_curricular.config.SwaggerConfig;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.CursoNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.SendIdException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.CursoDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Curso;
import com.rasmoo.cliente.escola.grade_curricular.services.CursoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = SwaggerConfig.CURSO)
@RestController
@RequestMapping("/api/cursos")
@CrossOrigin
@PreAuthorize(value = "#oauth2.hasScope('cw_logado')")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @ApiOperation(value = "Buscar todos os cursos.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de entidades exibida com sucesso."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @GetMapping
    public ResponseEntity<Page<CursoDTO>> listAllCursos(
            @PageableDefault(sort = "nome",
                    direction = Sort.Direction.ASC)
                    Pageable pageable){

        return new ResponseEntity<>(cursoService.listAll(pageable), HttpStatus.OK);

    }

    @ApiOperation(value = "Buscar curso por id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entidade encontrada."),
            @ApiResponse(code = 400, message = "Curso nao encontrado."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @GetMapping ("/{id}")
    public ResponseEntity<Curso> findCursoById(@PathVariable Long id) throws CursoNotFoundException {

        return new ResponseEntity<>(cursoService.findCursoById(id), HttpStatus.OK);

    }

    @ApiOperation(value = "Cadastrar um novo curso.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entidade criada com sucesso."),
            @ApiResponse(code = 400, message = "Erro na requisiçao enviada pelo cliente."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<MessageResponseDTO> createCurso(@Valid @RequestBody CursoDTO cursoDTO) throws SendIdException, MateriaNotFoundException {

        return new ResponseEntity<>(cursoService.createCurso(cursoDTO), HttpStatus.CREATED);

    }

    @ApiOperation(value = "Atualizar um curso.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entidade alterada com sucesso."),
            @ApiResponse(code = 400, message = "Erro na requisiçao enviada pelo cliente."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> updateCurso(@PathVariable Long id, @Valid @RequestBody CursoDTO cursoDTO) throws CursoNotFoundException, MateriaNotFoundException {

        return new ResponseEntity<>(cursoService.updateCurso(id, cursoDTO), HttpStatus.OK);

    }

    @ApiOperation(value = "Deletar um curso.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entidade deletada com sucesso."),
            @ApiResponse(code = 400, message = "Curso nao encontrado."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteCurso(@PathVariable Long id) throws CursoNotFoundException {

        return new ResponseEntity<>(cursoService.deleteCurso(id), HttpStatus.OK);

    }

}
