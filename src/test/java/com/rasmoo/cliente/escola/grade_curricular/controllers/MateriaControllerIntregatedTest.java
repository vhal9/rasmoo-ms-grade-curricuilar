package com.rasmoo.cliente.escola.grade_curricular.controllers;

import com.rasmoo.cliente.escola.grade_curricular.builders.MateriaMensagemResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.mappers.MateriaMapper;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.ResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import com.rasmoo.cliente.escola.grade_curricular.repositories.MateriaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class MateriaControllerIntregatedTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MateriaRepository materiaRepository;

    @BeforeEach
    void init() {
        this.populaBaseDeDados();
    }

    @AfterEach
    void finish() {
        this.materiaRepository.deleteAll();
    }

    private void populaBaseDeDados() {

        Materia materia1 = new Materia();
        materia1.setNome("Prog. Orientada a Objetos");
        materia1.setCodigo("POO");
        materia1.setHoras(2);

        Materia materia2 = new Materia();
        materia2.setNome("Inteligencia Artificial");
        materia2.setCodigo("IAL");
        materia2.setHoras(4);

        Materia materia3 = new Materia();
        materia3.setNome("Computaçao Grafica");
        materia3.setCodigo("COG");
        materia3.setHoras(4);

        this.materiaRepository.saveAll(Arrays.asList(materia1, materia2, materia3));

    }

    @Test
    public void quandoGETListarTodosEhChamadoERetornaListaDeMaterias() throws Exception {

        ResponseEntity<ResponseDTO<List<MateriaDTO>>> materias = restTemplate
                .exchange("http://localhost:" + this.port + "/api/materias/",
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<ResponseDTO<List<MateriaDTO>>>() {
                        });
        assertNotNull(materias.getBody().getData());
        assertEquals(200, materias.getBody().getHttpStatus());
        assertEquals(3, materias.getBody().getData().size());

    }

    @Test
    public void quandoGETListarTodosEhChamadoERetornaListaDeMateriasVazia() throws Exception {

        this.materiaRepository.deleteAll();

        ResponseEntity<ResponseDTO<List<MateriaDTO>>> materias = restTemplate
                .exchange("http://localhost:" + this.port + "/api/materias/",
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<ResponseDTO<List<MateriaDTO>>>() {
                        });
        assertNotNull(materias.getBody().getData());
        assertEquals(200, materias.getBody().getHttpStatus());
        assertEquals(0, materias.getBody().getData().size());

    }

    @Test
    public void quandoGETBuscarMateriaPorIdEhChamadoERetornaMateria() throws Exception {

        Long id = this.materiaRepository.findAll().get(0).getId();

        ResponseEntity<ResponseDTO<MateriaDTO>> materia = restTemplate
                .exchange("http://localhost:" + this.port + "/api/materias/" + id,
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<ResponseDTO<MateriaDTO>>() {});
        assertNotNull(materia.getBody().getData());
        assertEquals(200, materia.getBody().getHttpStatus());
        assertEquals(id, materia.getBody().getData().getId());
        assertEquals("Prog. Orientada a Objetos", materia.getBody().getData().getNome());
        assertEquals("POO", materia.getBody().getData().getCodigo());
        assertEquals(2, materia.getBody().getData().getHoras().intValue());

    }

    @Test
    public void quandoGETBuscarMateriaPorIdEhChamadoComIdInvalidoERetornaErro() throws Exception {

        Long id = this.materiaRepository.findAll().get(2).getId() + 1;

        ResponseEntity<ResponseDTO<String>> materia = restTemplate
                .exchange("http://localhost:" + this.port + "/api/materias/" + id,
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<ResponseDTO<String>>() {});
        assertNotNull(materia.getBody().getData());
        assertEquals(400, materia.getBody().getHttpStatus());

    }

    @Test
    public void quandoPUTAlterarMateriaEhChamadoERetornaSucesso() throws Exception {

        //given
        Materia materia = this.materiaRepository.findAll().get(0);
        materia.setNome("teste");
        MateriaDTO materiaDTO = MateriaMapper.INSTANCE.toDTO(materia);

        MessageResponseDTO expectedMenssage = MateriaMensagemResponseDTO
                .builder()
                .id(materia.getId())
                .build()
                .toResponsePut();

        HttpEntity<MateriaDTO> request = new HttpEntity<MateriaDTO>(materiaDTO);

        //then
        ResponseEntity<ResponseDTO<MessageResponseDTO>> response = restTemplate
                .exchange("http://localhost:" + this.port + "/api/materias/" + materia.getId(),
                        HttpMethod.PUT, request,
                        new ParameterizedTypeReference<ResponseDTO<MessageResponseDTO>>() {});

        Materia materiaAfterUpdate = this.materiaRepository.findAll().get(0);

        assertNotNull(response.getBody().getData());
        assertEquals(200, response.getBody().getHttpStatus());
        assertEquals(expectedMenssage, response.getBody().getData());
        assertEquals(materia.getNome(), materiaAfterUpdate.getNome());

    }

    @Test
    public void quandoPUTAlterarMateriaEhChamadoComIdInvalidoERetornaExcecao() throws Exception {

        //given
        Materia materia = this.materiaRepository.findAll().get(0);

        Long idInvalido = materia.getId() + 10;
        materia.setId(idInvalido);
        materia.setNome("teste");

        MateriaDTO materiaDTO = MateriaMapper.INSTANCE.toDTO(materia);

        String expectedMenssage = String.format("Subject with ID %s not found in the system.", idInvalido);

        HttpEntity<MateriaDTO> request = new HttpEntity<MateriaDTO>(materiaDTO);

        //then
        ResponseEntity<ResponseDTO<String>> response = restTemplate
                .exchange("http://localhost:" + this.port + "/api/materias/" + materia.getId(),
                        HttpMethod.PUT, request,
                        new ParameterizedTypeReference<ResponseDTO<String>>() {});

        Materia materiaAfterUpdate = this.materiaRepository.findAll().get(0);

        assertNotNull(response.getBody().getData());
        assertEquals(400, response.getBody().getHttpStatus());
        assertEquals(expectedMenssage, response.getBody().getData());
    
    }

    @Test
    public void quandoPOSTCadastrarMateriaEhChamadoERetornaSucesso() throws Exception {

        //given
        Long id = this.materiaRepository.findAll().get(2).getId() + 1;

        Materia materia = new Materia();
        materia.setNome("teste");
        materia.setHoras(2);
        materia.setCodigo("teste");

        MateriaDTO materiaDTO = MateriaMapper.INSTANCE.toDTO(materia);

        MessageResponseDTO expectedMessage = MateriaMensagemResponseDTO
                .builder()
                .id(id)
                .build()
                .toResponsePost();

        HttpEntity<MateriaDTO> request = new HttpEntity<MateriaDTO>(materiaDTO);

        //then
        ResponseEntity<ResponseDTO<MessageResponseDTO>> response = restTemplate
                .exchange("http://localhost:" + this.port + "/api/materias/",
                        HttpMethod.POST, request,
                        new ParameterizedTypeReference<ResponseDTO<MessageResponseDTO>>() {});

        assertNotNull(response.getBody().getData());
        assertEquals(201, response.getBody().getHttpStatus());
        assertEquals(expectedMessage, response.getBody().getData());

    }
    @Test
    public void quandoPOSTCadastrarMateriaComDadosInvalidosEhChamadoERetornaExcecao() throws Exception {

        Materia materia = new Materia();
        materia.setNome("");
        materia.setHoras(-2);
        materia.setCodigo("teste");

        MateriaDTO materiaDTO = MateriaMapper.INSTANCE.toDTO(materia);

        HttpEntity<MateriaDTO> request = new HttpEntity<MateriaDTO>(materiaDTO);

        //then
        ResponseEntity<ResponseDTO<List<String>>> response = restTemplate
                .exchange("http://localhost:" + this.port + "/api/materias/",
                        HttpMethod.POST, request,
                        new ParameterizedTypeReference<ResponseDTO<List<String>>>() {});

        assertNotNull(response.getBody().getData());
        assertEquals(400, response.getBody().getHttpStatus());

    }

    @Test
    public void quandoDELETEDeletarMateriaEhChamadoERetornaSucesso() throws Exception {

        //given
        List<Materia> materias = this.materiaRepository.findAll();
        Long id = materias.get(0).getId();

        MessageResponseDTO expectedMenssage = MateriaMensagemResponseDTO
                .builder()
                .id(id)
                .build()
                .toResponseDelete();

        //then
        ResponseEntity<ResponseDTO<MessageResponseDTO>> response = restTemplate
                .exchange("http://localhost:" + this.port + "/api/materias/" + id,
                        HttpMethod.DELETE, null,
                        new ParameterizedTypeReference<ResponseDTO<MessageResponseDTO>>() {});

        assertNotNull(response.getBody().getData());
        assertEquals(200, response.getBody().getHttpStatus());
        assertEquals(expectedMenssage, response.getBody().getData());
        assertNotEquals(materias.size(), this.materiaRepository.findAll().size());

    }

    @Test
    public void quandoDeleteDeletarMateriaEhChamadoComIdInvalidoERetornaExcecao() throws Exception {

        //given
        Long idInvalido = this.materiaRepository.findAll().get(0).getId() + 10;
        String expectedMessage = String.format("Subject with ID %s not found in the system.", idInvalido);

        //then
        ResponseEntity<ResponseDTO<String>> response = restTemplate
                .exchange("http://localhost:" + this.port + "/api/materias/" + idInvalido,
                        HttpMethod.DELETE, null,
                        new ParameterizedTypeReference<ResponseDTO<String>>() {});

        assertNotNull(response.getBody().getData());
        assertEquals(400, response.getBody().getHttpStatus());
        assertEquals(expectedMessage, response.getBody().getData());

    }
}
