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

    public Page<CursoDTO> listAll(Pageable pageable);
    public Curso findCursoById(Long id) throws CursoNotFoundException;
    public MessageResponseDTO createCurso(CursoDTO cursoDTO) throws SendIdException, MateriaNotFoundException;
    public MessageResponseDTO updateCurso(Long id, CursoDTO cursoDTO) throws CursoNotFoundException;
    public MessageResponseDTO deleteCurso(Long id) throws CursoNotFoundException;

}
