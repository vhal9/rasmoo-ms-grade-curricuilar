package com.rasmoo.cliente.escola.grade_curricular.services;

import com.rasmoo.cliente.escola.grade_curricular.builders.MateriaDTOBuilder;
import com.rasmoo.cliente.escola.grade_curricular.builders.MateriaMensagemResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.mappers.MateriaMapper;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import com.rasmoo.cliente.escola.grade_curricular.repositories.MateriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MateriaServiceUnitTest {

    private static final Long VALID_MATERIA_ID = 1L;
    private static final Long INVALID_MATERIA_ID = 2L;

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

    @Test
    public void quandoGetMateriaEhChamadoComIdValidoDeveSerRetornadoUmaMateria() throws MateriaNotFoundException {

        //given
        MateriaDTO expectedFoundMateriaDTO = MateriaDTOBuilder.builder().build().toMateriaDTO();
        Materia expectedFoundMateria = materiaMapper.toModel(expectedFoundMateriaDTO);

        //when
        when(materiaRepository.findById(VALID_MATERIA_ID)).thenReturn(Optional.of(expectedFoundMateria));

        //then
        MateriaDTO materiaDTORetornada = materiaService.getMateriaById(VALID_MATERIA_ID);

        assertThat(materiaDTORetornada, is(equalTo(expectedFoundMateriaDTO)));

    }

    @Test
    public void quandoGetMateriaEhChamadoComIdInvalidoDeveSerRetornadoExcecao() throws MateriaNotFoundException {

        //when
        when(materiaRepository.findById(INVALID_MATERIA_ID)).thenReturn(Optional.empty());

        //then
        assertThrows(MateriaNotFoundException.class, () -> materiaService.getMateriaById(INVALID_MATERIA_ID));

    }

    @Test
    public void quandoFindMateriasEhChamadoComIdsValidosDeveSerRetornadoUmaListaDeMaterias() throws MateriaNotFoundException {

        //given
        List<Long> listaDeIds = new ArrayList<>();
        listaDeIds.add(1L);
        listaDeIds.add(2L);

        MateriaDTO expectedFoundMateriaDTO1 = MateriaDTOBuilder.builder().build().toMateriaDTO();
        MateriaDTO expectedFoundMateriaDTO2 = MateriaDTOBuilder.builder().build().toMateriaDTO();
        expectedFoundMateriaDTO2.setId(2L);

        Materia expectedFoundMateria1 = materiaMapper.toModel(expectedFoundMateriaDTO1);
        Materia expectedFoundMateria2 = materiaMapper.toModel(expectedFoundMateriaDTO2);

        //when
        when(materiaRepository.findById(1L)).thenReturn(Optional.of(expectedFoundMateria1));
        when(materiaRepository.findById(2L)).thenReturn(Optional.of(expectedFoundMateria2));

        //then
        List<Materia> materiasRetornada = materiaService.findMateriasByIds(listaDeIds);

        assertThat(materiasRetornada, is(not(empty())));
        assertThat(materiasRetornada.get(0), is(equalTo(expectedFoundMateria1)));
        assertThat(materiasRetornada.get(1), is(equalTo(expectedFoundMateria2)));

    }

    @Test
    public void quandoFindMateriasEhChamadoComIdsInvalidosDeveSerRetornadoUmaExcecao() throws MateriaNotFoundException {

        //given
        List<Long> listaDeIds = new ArrayList<>();
        listaDeIds.add(INVALID_MATERIA_ID);

        //when
        when(materiaRepository.findById(INVALID_MATERIA_ID)).thenReturn(Optional.empty());

        //then
        assertThrows(MateriaNotFoundException.class, () -> materiaService.findMateriasByIds(listaDeIds));

    }

    @Test
    public void quandoCriarMateriaEhChamadoEntaoRetornaSucesso() {

        //given
        MateriaDTO materiaDTO = MateriaDTOBuilder.builder().build().toMateriaDTO();
        Materia expectedSavedMateria = materiaMapper.toModel(materiaDTO);
        MessageResponseDTO expectedSavedMessage = MateriaMensagemResponseDTO.builder().build().toResponsePost();

        //when
        when(materiaRepository.save(expectedSavedMateria)).thenReturn(expectedSavedMateria);

        //then
        MessageResponseDTO createdMessage  = materiaService.createMateria(materiaDTO);

        assertThat(createdMessage, is(equalTo(expectedSavedMessage)));

    }



}
