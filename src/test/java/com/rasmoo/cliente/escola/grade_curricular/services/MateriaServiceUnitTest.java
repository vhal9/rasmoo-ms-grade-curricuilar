package com.rasmoo.cliente.escola.grade_curricular.services;

import com.rasmoo.cliente.escola.grade_curricular.builders.MateriaDTOBuilder;
import com.rasmoo.cliente.escola.grade_curricular.mappers.MateriaMapper;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import com.rasmoo.cliente.escola.grade_curricular.repositories.MateriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MateriaServiceUnitTest {

    @Mock
    private MateriaRepository materiaRepository;

    MateriaMapper materiaMapper = MateriaMapper.INSTANCE;

    @InjectMocks
    private MateriaService materiaService;

    @Test
    public void quandoListarTodasMateriasEhChamadoUmaListaDeveSerRetornada() {

        //given
        MateriaDTO expectedFoundMateriaDTO = MateriaDTOBuilder.builder().build().toMateriaDTO();
        Materia expectedFoundMateria = materiaMapper.toModel(expectedFoundMateriaDTO);

        //when
        when(materiaRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundMateria));

        //then
        List<MateriaDTO> materiaDTORetornada = materiaService.listMaterias();

        assertThat(materiaDTORetornada, is(not(empty())));
        assertThat(materiaDTORetornada.get(0), is(equalTo(expectedFoundMateriaDTO)));

    }

    @Test
    public void quandoListarTodasMateriasEhChamadoUmaListaVaziaDeveSerRetornada() {


        //when
        when(materiaRepository.findAll()).thenReturn(Collections.emptyList());

        //then
        List<MateriaDTO> materiaDTORetornada = materiaService.listMaterias();

        assertThat(materiaDTORetornada, is(empty()));


    }


}
