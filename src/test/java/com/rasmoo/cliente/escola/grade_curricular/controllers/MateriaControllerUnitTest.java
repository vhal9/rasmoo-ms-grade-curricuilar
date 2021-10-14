package com.rasmoo.cliente.escola.grade_curricular.controllers;

import com.rasmoo.cliente.escola.grade_curricular.builders.MateriaDTOBuilder;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.services.MateriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MateriaControllerUnitTest {

    private static final String MOVIE_API_URL_PATH = "/api/materias";

    private MockMvc mockMvc;

    @Mock
    private MateriaService materiaService;

    @InjectMocks
    private MateriaController materiaController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(materiaController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    public void QuandoGETListarTodosEhChamado() throws Exception {

        //given
        MateriaDTO materiaDTO = MateriaDTOBuilder.builder().build().toMateriaDTO();

        //when
        when(materiaService.listMaterias()).thenReturn(Collections.singletonList(materiaDTO));

        //then
        mockMvc.perform(get(MOVIE_API_URL_PATH )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus", is(200)))
                .andExpect(jsonPath("$.data[0].id", is(materiaDTO.getId().intValue())))
                .andExpect(jsonPath("$.data[0].nome", is(materiaDTO.getNome())))
                .andExpect(jsonPath("$.data[0].horas", is(materiaDTO.getHoras())))
                .andExpect(jsonPath("$.data[0].codigo", is(materiaDTO.getCodigo())));

    }

    @Test
    public void QuandoGETListarTodosSemMateriasEhChamadoEListaVazioEhRetornado() throws Exception {

        //when
        when(materiaService.listMaterias()).thenReturn(Collections.emptyList());

        //then
        mockMvc.perform(get(MOVIE_API_URL_PATH )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus", is(200)))
                .andExpect(jsonPath("$.data", is(empty())));

    }

}
