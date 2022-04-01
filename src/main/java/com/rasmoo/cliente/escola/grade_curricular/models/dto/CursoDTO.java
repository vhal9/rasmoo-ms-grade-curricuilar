package com.rasmoo.cliente.escola.grade_curricular.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CursoDTO {

    private Long id;

    @NotEmpty(message = "{curso.campo-nome.obrigatorio}")
    @Size(min = 3, max = 30, message = "{curso.campo-nome.tamanho}")
    private String nome;

    @NotEmpty(message = "{curso.campo-codigo.obrigatorio}")
    @Size(min = 3, max = 10, message = "{curso.campo-codigo.tamanho}")
    private String codigo;

    private List<Long> idsMaterias = new ArrayList<>();

}
