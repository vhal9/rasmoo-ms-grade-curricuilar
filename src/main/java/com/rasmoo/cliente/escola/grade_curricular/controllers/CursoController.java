package com.rasmoo.cliente.escola.grade_curricular.controllers;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.CursoNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.SendIdException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.CursoDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.ResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Curso;
import com.rasmoo.cliente.escola.grade_curricular.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private CursoService cursoService;

    @Autowired
    CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

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

    @GetMapping ("/{id}")
    public ResponseDTO<Curso> findCursoById(@PathVariable Long id) throws CursoNotFoundException {

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(cursoService.findCursoById(id));
        responseDTO.setHttpStatus(HttpStatus.OK.value());

        return responseDTO;

    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<MessageResponseDTO> createCurso(@Valid @RequestBody CursoDTO cursoDTO) throws MateriaNotFoundException, SendIdException {

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(cursoService.createCurso(cursoDTO));
        responseDTO.setHttpStatus(HttpStatus.CREATED.value());

        return responseDTO;

    }


}
