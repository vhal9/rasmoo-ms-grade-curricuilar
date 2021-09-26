package com.rasmoo.cliente.escola.grade_curricular.mappers;

import com.rasmoo.cliente.escola.grade_curricular.models.dto.CursoDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CursoMapper {

    CursoMapper INSTANCE = Mappers.getMapper(CursoMapper.class);

    Curso toModel(CursoDTO cursoDTO);

    CursoDTO toDTO(Curso curso);

}
