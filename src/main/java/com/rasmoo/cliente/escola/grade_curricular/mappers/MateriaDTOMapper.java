package com.rasmoo.cliente.escola.grade_curricular.mappers;

import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import org.springframework.stereotype.Component;

@Component
public interface MateriaDTOMapper {
    public MateriaDTO execute(Materia materia);
}
