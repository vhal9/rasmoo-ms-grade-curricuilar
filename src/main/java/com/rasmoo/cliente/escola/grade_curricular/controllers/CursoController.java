package com.rasmoo.cliente.escola.grade_curricular.controllers;

import com.rasmoo.cliente.escola.grade_curricular.config.SwaggerConfig;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.CursoNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.SendIdException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.CursoDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.ResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Curso;
import com.rasmoo.cliente.escola.grade_curricular.services.CursoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = SwaggerConfig.CURSO)
@RestController
@RequestMapping("/api/cursos")
@CrossOrigin
@PreAuthorize(value = "#oauth2.hasScope('cw_logado') and hasRole('ROLE_CUSTOMER')")
public class CursoController {

    private CursoService cursoService;

    @Autowired
    CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @ApiOperation(value = "Buscar todos os cursos.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de entidades exibida com sucesso."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @GetMapping
    public ResponseDTO<Page<Curso>> listAllCursos(
            @PageableDefault(sort = "nome",
                    direction = Sort.Direction.ASC,
                    page = 0,
                    size = 5)
                    Pageable pageable){

        ResponseDTO responseDTO = new ResponseDTO<>();
        responseDTO.setData(cursoService.listAll(pageable));
        responseDTO.setHttpStatus(HttpStatus.OK.value());

        return responseDTO;

    }

    @ApiOperation(value = "Buscar curso por id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entidade encontrada."),
            @ApiResponse(code = 400, message = "Curso nao encontrado."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @GetMapping ("/{id}")
    public ResponseDTO<Curso> findCursoById(@PathVariable Long id) throws CursoNotFoundException {

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(cursoService.findCursoById(id));
        responseDTO.setHttpStatus(HttpStatus.OK.value());

        return responseDTO;

    }

    @ApiOperation(value = "Cadastrar um novo curso.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entidade criada com sucesso."),
            @ApiResponse(code = 400, message = "Erro na requisiçao enviada pelo cliente."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<MessageResponseDTO> createCurso(@Valid @RequestBody CursoDTO cursoDTO) throws SendIdException {

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(cursoService.createCurso(cursoDTO));
        responseDTO.setHttpStatus(HttpStatus.CREATED.value());

        return responseDTO;

    }

    @ApiOperation(value = "Atualizar um curso.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entidade alterada com sucesso."),
            @ApiResponse(code = 400, message = "Erro na requisiçao enviada pelo cliente."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @PutMapping("/{id}")
    public ResponseDTO<MessageResponseDTO> updateCurso(@PathVariable Long id, @Valid @RequestBody CursoDTO cursoDTO) throws CursoNotFoundException {

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(cursoService.updateCurso(id, cursoDTO));
        responseDTO.setHttpStatus(HttpStatus.OK.value());

        return responseDTO;

    }

    @ApiOperation(value = "Deletar um curso.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entidade deletada com sucesso."),
            @ApiResponse(code = 400, message = "Curso nao encontrado."),
            @ApiResponse(code = 500, message = "Erro interno do servidor")

    })
    @DeleteMapping("/{id}")
    public ResponseDTO<MessageResponseDTO> deleteCurso(@PathVariable Long id) throws CursoNotFoundException {

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(cursoService.deleteCurso(id));
        responseDTO.setHttpStatus(HttpStatus.OK.value());

        return responseDTO;

    }

}
