package com.rasmoo.cliente.escola.grade_curricular.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CursoDTO {

    private Long id;

    @NotEmpty(message = "Campo nome é obrigatório")
    @Size(min = 3, max = 30, message = "Campo nome deve ter entre 3 e 30 caracteres")
    private String nome;

    @NotEmpty(message = "Campo código é obrigatório")
    @Size(min = 3, max = 5, message = "Campo código deve ter entre 3 e 5 caracteres")
    private String codigo;

    private List<Long> idsMaterias;

}
