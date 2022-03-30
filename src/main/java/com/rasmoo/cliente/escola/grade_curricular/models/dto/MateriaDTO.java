package com.rasmoo.cliente.escola.grade_curricular.models.dto;

import com.rasmoo.cliente.escola.grade_curricular.utils.Messages;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MateriaDTO {

    private Long id;

    @NotEmpty(message = Messages.MATERIA_CAMPO_NOME_OBRIGATORIO)
    @Size(min = 3, max = 30, message = Messages.MATERIA_CAMPO_NOME_TAMANHO)
    private String nome;

    @NotNull(message = Messages.MATERIA_CAMPO_HORAS_OBRIGATORIO)
    @Min(value = 1, message = Messages.MATERIA_CAMPO_HORAS_TAMANHO)
    private Integer horas;

    @NotEmpty(message = Messages.MATERIA_CAMPO_CODIGO_OBRIGATORIO)
    @Size(min = 3, max = 10, message = Messages.MATERIA_CAMPO_CODIGO_TAMANHO)
    private String codigo;

}
