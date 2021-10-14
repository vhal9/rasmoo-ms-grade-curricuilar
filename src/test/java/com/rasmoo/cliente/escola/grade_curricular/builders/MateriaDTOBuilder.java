package com.rasmoo.cliente.escola.grade_curricular.builders;

import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import lombok.Builder;

@Builder
public class MateriaDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String nome = "informatica";

    @Builder.Default
    private Integer horas = 3;

    @Builder.Default
    private String codigo = "INF";

    public MateriaDTO toMateriaDTO() {
        return new MateriaDTO(id,
                nome,
                horas,
                codigo);
    }
}
