package com.rasmoo.cliente.escola.grade_curricular.builders;

import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import lombok.Builder;

@Builder
public class MateriaBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String nome = "informatica";

    @Builder.Default
    private Integer horas = 3;

    @Builder.Default
    private String codigo = "INF";

    public Materia toMateria() {
        return new Materia(id,
                nome,
                horas,
                codigo);
    }
}
