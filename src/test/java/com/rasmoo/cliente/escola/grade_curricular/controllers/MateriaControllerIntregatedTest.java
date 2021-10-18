package com.rasmoo.cliente.escola.grade_curricular.controllers;

import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


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

        this.finish();

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

        ResponseEntity<ResponseDTO<MateriaDTO>> materia = restTemplate
                .exchange("http://localhost:" + this.port + "/api/materias/1",
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<ResponseDTO<MateriaDTO>>() {});
        assertNotNull(materia.getBody().getData());
        assertEquals(200, materia.getBody().getHttpStatus());
        assertEquals(1, materia.getBody().getData().getId().intValue());
        assertEquals("Prog. Orientada a Objetos", materia.getBody().getData().getNome());
        assertEquals("POO", materia.getBody().getData().getCodigo());
        assertEquals(2, materia.getBody().getData().getHoras().intValue());

    }

    @Test
    public void quandoGETBuscarMateriaPorIdEhChamadoComIdInvalidoERetornaErro() throws Exception {

        ResponseEntity<ResponseDTO<String>> materia = restTemplate
                .exchange("http://localhost:" + this.port + "/api/materias/4",
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<ResponseDTO<String>>() {});
        assertNotNull(materia.getBody().getData());
        assertEquals(400, materia.getBody().getHttpStatus());

    }
}
