package com.rasmoo.cliente.escola.grade_curricular.models.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CredencialUsuarioDTO {

    @NotEmpty(message = "Campo email é obrigatório")
    private String email;

    @NotEmpty(message = "Campo senha é obrigatório")
    @Size(min = 8, max = 20, message = "Campo senha deve ter entre 8 e 20 caracteres")
    private String senha;

}
