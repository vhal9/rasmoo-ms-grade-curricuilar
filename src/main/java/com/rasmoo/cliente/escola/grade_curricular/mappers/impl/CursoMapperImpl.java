package com.rasmoo.cliente.escola.grade_curricular.mappers.impl;

import com.rasmoo.cliente.escola.grade_curricular.mappers.CursoMapper;
import com.rasmoo.cliente.escola.grade_curricular.mappers.MateriaMapper;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.CursoDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Curso;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CursoMapperImpl implements CursoMapper {

    private final MateriaMapper materiaMapper;

    @Override
    public Curso execute(CursoDTO cursoDTO) {
        return Curso.builder()
                .id(cursoDTO.getId())
                .nome(cursoDTO.getNome())
                .codigo(cursoDTO.getCodigo())
                .materias(getMaterias(cursoDTO.getMaterias()))
                .build();
    }

    private List<Materia> getMaterias(List<MateriaDTO> materiaDTOs) {
        return Objects.nonNull(materiaDTOs)
                ? materiaDTOs.stream().map(materiaMapper::execute).collect(Collectors.toList())
                : Collections.emptyList();
    }
}
