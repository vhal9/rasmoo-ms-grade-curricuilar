package com.rasmoo.cliente.escola.grade_curricular.controllers;

import com.rasmoo.cliente.escola.grade_curricular.builders.MateriaDTOBuilder;
import com.rasmoo.cliente.escola.grade_curricular.builders.MateriaMensagemResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
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

import static com.rasmoo.cliente.escola.grade_curricular.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MateriaControllerUnitTest {

    private static final String MOVIE_API_URL_PATH = "/api/materias";
    private static final Long VALID_MATERIA_ID = 1L;

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
    public void quandoGETListarTodosEhChamado() throws Exception {

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
    public void quandoGETListarTodosSemMateriasEhChamadoEListaVazioEhRetornado() throws Exception {

        //when
        when(materiaService.listMaterias()).thenReturn(Collections.emptyList());

        //then
        mockMvc.perform(get(MOVIE_API_URL_PATH )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus", is(200)))
                .andExpect(jsonPath("$.data", is(empty())));

    }

    @Test
    public void quandoGETbuscarMateriaPeloIdEhChamadoERetornaSucesso() throws Exception {

        //given
        MateriaDTO materiaDTO = MateriaDTOBuilder.builder().build().toMateriaDTO();

        //when
        when(materiaService.getMateriaById(materiaDTO.getId())).thenReturn(materiaDTO);

        //then
        mockMvc.perform(get(MOVIE_API_URL_PATH + "/" + materiaDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus", is(200)))
                .andExpect(jsonPath("$.data.id", is(materiaDTO.getId().intValue())))
                .andExpect(jsonPath("$.data.nome", is(materiaDTO.getNome())))
                .andExpect(jsonPath("$.data.horas", is(materiaDTO.getHoras())))
                .andExpect(jsonPath("$.data.codigo", is(materiaDTO.getCodigo())));
    }

    @Test
    public void quandoGETbuscarMateriaPeloIdEhChamadoERetornaUmaExcecao() throws Exception {

        //given
        MateriaDTO materiaDTO = MateriaDTOBuilder.builder().build().toMateriaDTO();

        //when
        when(materiaService.getMateriaById(materiaDTO.getId())).thenThrow(MateriaNotFoundException.class);

        //then
        mockMvc.perform(get(MOVIE_API_URL_PATH + "/" + materiaDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }

    @Test
    public void quandoPOSTEhChamadoERetornaSucesso() throws Exception {

        //given
        MateriaDTO materiaDTO = MateriaDTOBuilder.builder().build().toMateriaDTO();
        materiaDTO.setId(null);
        MessageResponseDTO expectedMessageResponse = MateriaMensagemResponseDTO.builder().build().toResponsePost();

        //when
        when(materiaService.createMateria(materiaDTO)).thenReturn(expectedMessageResponse);

        //then
        mockMvc.perform(post(MOVIE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(materiaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.httpStatus", is(201)))
                .andExpect(jsonPath("$.data.id", is(expectedMessageResponse.getId().intValue())))
                .andExpect(jsonPath("$.data.message", is(expectedMessageResponse.getMessage())));
    }

    @Test
    public void quandoPOSTEhChamadoComDadosInvalidosEntaoRetornaExcecao() throws Exception {

        //given
        MateriaDTO materiaDTO = MateriaDTOBuilder.builder().build().toMateriaDTO();
        materiaDTO.setId(null);
        materiaDTO.setNome("");
        materiaDTO.setCodigo("");
        materiaDTO.setHoras(0);

        //then
        mockMvc.perform(post(MOVIE_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(materiaDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void quandoPOSTEhChamadoEnviandoIdEntaoRetornaExcecao() throws Exception {

        //given
        MateriaDTO materiaDTO = MateriaDTOBuilder.builder().build().toMateriaDTO();

        //then
        mockMvc.perform(post(MOVIE_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(materiaDTO)))
                .andExpect(status().isBadRequest());
    }

}
