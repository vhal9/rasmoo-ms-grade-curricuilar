package com.rasmoo.cliente.escola.grade_curricular.mappers.impl;

import com.rasmoo.cliente.escola.grade_curricular.mappers.CursoDTOMapper;
import com.rasmoo.cliente.escola.grade_curricular.mappers.MateriaDTOMapper;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.CursoDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Curso;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CursoDTOMapperImpl implements CursoDTOMapper {

    private final MateriaDTOMapper materiaDTOMapper;

    @Override
    public CursoDTO execute(Curso curso) {
        return CursoDTO.builder()
                .id(curso.getId())
                .nome(curso.getNome())
                .codigo(curso.getCodigo())
                .materias(getMateriasDTO(curso.getMaterias()))
                .build();
    }

    private List<MateriaDTO> getMateriasDTO(List<Materia> materias) {
        return Objects.nonNull(materias)
                ? materias.stream().map(materiaDTOMapper::execute).collect(Collectors.toList())
                : Collections.emptyList();
    }
}
