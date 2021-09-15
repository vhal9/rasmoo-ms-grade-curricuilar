package com.rasmoo.cliente.escola.grade_curricular.mappers;

import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MateriaMapper {

    MateriaMapper INSTANCE = Mappers.getMapper(MateriaMapper.class);

    Materia toModel(MateriaDTO materiaDTO);

    MateriaDTO toDTO(Materia materia);

}
