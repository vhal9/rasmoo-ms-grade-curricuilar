package com.rasmoo.cliente.escola.grade_curricular.mappers;

import com.rasmoo.cliente.escola.grade_curricular.models.dto.CursoDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Curso;
import org.springframework.stereotype.Component;

@Component
public interface CursoMapper {
    Curso execute(CursoDTO cursoDTO);
}
