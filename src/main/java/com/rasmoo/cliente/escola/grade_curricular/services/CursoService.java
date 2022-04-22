package com.rasmoo.cliente.escola.grade_curricular.services;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.CursoNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.SendIdException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.CursoDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CursoService {

    Page<CursoDTO> listAll(Pageable pageable);
    Curso findCursoById(Long id) throws CursoNotFoundException;
    MessageResponseDTO createCurso(CursoDTO cursoDTO) throws SendIdException, MateriaNotFoundException;
    MessageResponseDTO updateCurso(Long id, CursoDTO cursoDTO) throws CursoNotFoundException, MateriaNotFoundException;
    MessageResponseDTO deleteCurso(Long id) throws CursoNotFoundException;

}
