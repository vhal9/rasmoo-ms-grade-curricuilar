package com.rasmoo.cliente.escola.grade_curricular.services;

import com.rasmoo.cliente.escola.grade_curricular.builders.MateriaBuilder;
import com.rasmoo.cliente.escola.grade_curricular.builders.MateriaDTOBuilder;
import com.rasmoo.cliente.escola.grade_curricular.builders.MateriaMensagemResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.mappers.impl.MateriaDTOMapperImpl;
import com.rasmoo.cliente.escola.grade_curricular.mappers.impl.MateriaMapperImpl;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import com.rasmoo.cliente.escola.grade_curricular.repositories.MateriaRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MateriaServiceUnitTest {

    private static final Long VALID_MATERIA_ID = 1L;
    private static final Long INVALID_MATERIA_ID = 2L;

    @Mock
    private MateriaRepository materiaRepository;

    @Mock
    private MateriaDTOMapperImpl materiaDTOMapper;

    @Mock
    private MateriaMapperImpl materiaMapper;

    @InjectMocks
    private MateriaService materiaService;

    @Test
    public void QuandoListarTodasMateriasEhChamadoUmaListaDeveSerRetornada() {
        //given
        Materia materiaMock = MateriaBuilder.builder().build().toMateria();
        MateriaDTO expectedFoundMateriaDTO = MateriaDTOBuilder.builder().build().toMateriaDTO();

        when(materiaRepository.findAll()).thenReturn(Collections.singletonList(materiaMock));
        when(materiaDTOMapper.execute(materiaMock)).thenReturn(expectedFoundMateriaDTO);

        //when
        List<MateriaDTO> listaMateriasRetornada = materiaService.listMaterias();

        //then
        verify(materiaRepository, times(1)).findAll();
        verify(materiaDTOMapper, times(1)).execute(materiaMock);
        assertThat(listaMateriasRetornada, is(not(empty())));
        assertThat(listaMateriasRetornada.size(), is(equalTo(1)));
        assertThat(listaMateriasRetornada.get(0), is(equalTo(expectedFoundMateriaDTO)));

    }

    @Test
    public void quandoListarTodasMateriasEhChamadoUmaListaVaziaDeveSerRetornada() {

        //given
        when(materiaRepository.findAll()).thenReturn(Collections.emptyList());

        //when
        List<MateriaDTO> listaMateriasRetornada = materiaService.listMaterias();

        //then
        verify(materiaRepository, times(1)).findAll();
        assertThat(listaMateriasRetornada, is(empty()));
        assertThat(listaMateriasRetornada.size(), is(equalTo(0)));

    }

    @Test
    public void quandoBuscarMateriaComIdValidoAMateriaDeveSerRetornada() throws MateriaNotFoundException {

        //given
        Materia materiaMock = MateriaBuilder.builder().build().toMateria();
        MateriaDTO expectedFoundMateriaDTO = MateriaDTOBuilder.builder().build().toMateriaDTO();

        when(materiaRepository.findById(VALID_MATERIA_ID)).thenReturn(Optional.of(materiaMock));
        when(materiaDTOMapper.execute(materiaMock)).thenReturn(expectedFoundMateriaDTO);

        //when
        MateriaDTO materiaDTORetornada = materiaService.getMateriaById(VALID_MATERIA_ID);

        //then
        verify(materiaRepository, times(1)).findById(VALID_MATERIA_ID);
        verify(materiaDTOMapper, times(1)).execute(materiaMock);
        assertThat(materiaDTORetornada, is(equalTo(expectedFoundMateriaDTO)));

    }

    @Test
    public void quandoBuscarMateriaComIdInvalidoUmaExcecaoDeveSerRetornada() {

        //given
        when(materiaRepository.findById(INVALID_MATERIA_ID)).thenReturn(Optional.empty());

        //then
        assertThrows(MateriaNotFoundException.class, () -> materiaService.getMateriaById(INVALID_MATERIA_ID));
        verify(materiaRepository, times(1)).findById(INVALID_MATERIA_ID);
        verify(materiaDTOMapper, times(0)).execute(any());

    }

    @Test
    public void quandoCriarMateriaEhChamadoEntaoRetornaMensagemDeSucesso() {

        //given
        MateriaDTO materiaASerCriadaDTO = MateriaDTOBuilder.builder().build().toMateriaDTO();
        Materia materiaASerCriada = MateriaBuilder.builder().build().toMateria();
        MessageResponseDTO mensagemEsperada = MateriaMensagemResponseDTO.builder().build().toResponsePost();

        when(materiaMapper.execute(materiaASerCriadaDTO)).thenReturn(materiaASerCriada);
        when(materiaRepository.save(materiaASerCriada)).thenReturn(materiaASerCriada);

        //when
        MessageResponseDTO mensagemRetornada = materiaService.createMateria(materiaASerCriadaDTO);

        //then
        verify(materiaMapper, times(1)).execute(materiaASerCriadaDTO);
        verify(materiaRepository, times(1)).save(materiaASerCriada);
        assertThat(mensagemRetornada, is(equalTo(mensagemEsperada)));

    }

}
