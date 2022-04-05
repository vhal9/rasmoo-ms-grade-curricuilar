package com.rasmoo.cliente.escola.grade_curricular.mappers.impl;

import com.rasmoo.cliente.escola.grade_curricular.mappers.MateriaDTOMapper;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import org.springframework.stereotype.Component;

@Component
public class MateriaDTOMapperImpl implements MateriaDTOMapper {

    @Override
    public MateriaDTO execute(Materia materia) {
        return MateriaDTO.builder()
                .id(materia.getId())
                .nome(materia.getNome())
                .horas(materia.getHoras())
                .codigo(materia.getCodigo())
                .build();
    }
}
