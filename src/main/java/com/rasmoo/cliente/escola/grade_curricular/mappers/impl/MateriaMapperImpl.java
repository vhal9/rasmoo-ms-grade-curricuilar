package com.rasmoo.cliente.escola.grade_curricular.mappers.impl;

import com.rasmoo.cliente.escola.grade_curricular.mappers.MateriaMapper;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import org.springframework.stereotype.Component;

@Component
public class MateriaMapperImpl implements MateriaMapper {

    @Override
    public Materia execute(MateriaDTO materiaDTO) {
        return Materia.builder()
                .id(materiaDTO.getId())
                .nome(materiaDTO.getNome())
                .horas(materiaDTO.getHoras())
                .codigo(materiaDTO.getCodigo())
                .build();
    }

}
