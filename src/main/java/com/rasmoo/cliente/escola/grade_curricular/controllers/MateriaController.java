package com.rasmoo.cliente.escola.grade_curricular.controllers;

import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import com.rasmoo.cliente.escola.grade_curricular.services.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public Page<MateriaDTO> listMaterias (
            @PageableDefault(sort = "nome",
                    direction = Sort.Direction.ASC,
                    page = 0,
                    size = 5)
                    Pageable pageable) {

        return materiaService.listMaterias(pageable);

    }

    @PostMapping
    public MessageResponseDTO createMateria(@Valid @RequestBody MateriaDTO materiaDTO) {

        return  materiaService.createMateria(materiaDTO);

    }

}
