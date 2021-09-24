package com.rasmoo.cliente.escola.grade_curricular.controllers;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.ResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import com.rasmoo.cliente.escola.grade_curricular.services.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/materias")
public class MateriaController {

    private MateriaService materiaService;

    @Autowired
    MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @GetMapping
    public ResponseDTO<Page<MateriaDTO>> listMaterias (
            @PageableDefault(sort = "nome",
                    direction = Sort.Direction.ASC,
                    page = 0,
                    size = 5)
                    Pageable pageable) {

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(materiaService.listMaterias(pageable));
        responseDTO.setHttpStatus(HttpStatus.OK.value());

        return responseDTO;

    }

    @GetMapping("{id}")
    public ResponseDTO<MateriaDTO> getMateriaById(@PathVariable Long id) throws MateriaNotFoundException {

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(materiaService.getMateriaById(id));
        responseDTO.setHttpStatus(HttpStatus.OK.value());

        return responseDTO;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<MessageResponseDTO> createMateria(@Valid @RequestBody MateriaDTO materiaDTO) {

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(materiaService.createMateria(materiaDTO));
        responseDTO.setHttpStatus(HttpStatus.CREATED.value());

        return responseDTO;

    }

    @PutMapping("{id}")
    public MessageResponseDTO update(@Valid @PathVariable Long id, @RequestBody MateriaDTO materiaDTO) throws MateriaNotFoundException {

        return materiaService.updateMateria(id, materiaDTO);

    }

    @DeleteMapping("{id}")
    public MessageResponseDTO delete(@PathVariable Long id) throws MateriaNotFoundException {

        return materiaService.deleteMateriaById(id);

    }

}
