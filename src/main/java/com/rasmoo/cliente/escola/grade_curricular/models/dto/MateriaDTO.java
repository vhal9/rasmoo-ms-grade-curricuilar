package com.rasmoo.cliente.escola.grade_curricular.models.dto;

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

    @NotEmpty(message = "Nome field is required")
    @Size(min = 3, max = 30, message = "Nome field must be between 3 and 30")
    private String nome;

    @NotNull(message = "Horas field is required")
    @Min(value = 0, message = "Horas field must be greater than 0")
    private Integer horas;

    @NotEmpty(message = "Codigo field is required")
    @Size(min = 3, max = 10, message = "Codigo field must be between 3 and 10")
    private String codigo;

}
