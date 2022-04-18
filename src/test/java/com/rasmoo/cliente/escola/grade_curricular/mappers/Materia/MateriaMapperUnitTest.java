package com.rasmoo.cliente.escola.grade_curricular.mappers.Materia;

import com.rasmoo.cliente.escola.grade_curricular.builders.MateriaDTOBuilder;
import com.rasmoo.cliente.escola.grade_curricular.mappers.impl.MateriaMapperImpl;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class MateriaMapperUnitTest {

    private final MateriaMapperImpl materiaMapper;

    public MateriaMapperUnitTest(){
        this.materiaMapper = new MateriaMapperImpl();
    }

    @Test
    public void deveRetornarMateria() {

        MateriaDTO materiaDTO = MateriaDTOBuilder.builder().build().toMateriaDTO();

        Materia materia = materiaMapper.execute(materiaDTO);

        assertThat(materia)
                .hasFieldOrPropertyWithValue("id", materia.getId())
                .hasFieldOrPropertyWithValue("nome", materia.getNome())
                .hasFieldOrPropertyWithValue("horas", materia.getHoras())
                .hasFieldOrPropertyWithValue("codigo", materia.getCodigo());

    }
}
