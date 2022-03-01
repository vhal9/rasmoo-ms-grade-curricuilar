package com.rasmoo.cliente.escola.grade_curricular.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    @NotEmpty(message = "Campo nome é obrigatório")
    @Size(min = 3, max = 30, message = "Campo nome deve ter entre 3 e 30 caracteres")
    private String nome;

    @NotEmpty(message = "Campo email é obrigatório")
    @Size(min = 8, max = 50, message = "Campo email deve ter entre 8 e 50 caracteres")
    private String email;

    @NotEmpty(message = "Campo senha é obrigatório")
    @Size(min = 8, max = 20, message = "Campo senha deve ter entre 8 e 20 caracteres")
    private String senha;
}
