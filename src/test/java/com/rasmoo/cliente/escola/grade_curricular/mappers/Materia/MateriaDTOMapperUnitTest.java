package com.rasmoo.cliente.escola.grade_curricular.mappers.Materia;

import com.rasmoo.cliente.escola.grade_curricular.builders.MateriaBuilder;
import com.rasmoo.cliente.escola.grade_curricular.mappers.impl.MateriaDTOMapperImpl;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class MateriaDTOMapperUnitTest {

    private final MateriaDTOMapperImpl materiaDTOMapper;

    public MateriaDTOMapperUnitTest(){
        this.materiaDTOMapper = new MateriaDTOMapperImpl();
    }

    @Test
    public void deveRetornarMateriaDTO() {

        Materia materia = MateriaBuilder.builder().build().toMateria();

        MateriaDTO materiaDTO = materiaDTOMapper.execute(materia);

        assertThat(materiaDTO)
                .hasFieldOrPropertyWithValue("id", materiaDTO.getId())
                .hasFieldOrPropertyWithValue("nome", materiaDTO.getNome())
                .hasFieldOrPropertyWithValue("horas", materiaDTO.getHoras())
                .hasFieldOrPropertyWithValue("codigo", materiaDTO.getCodigo());

    }

}
