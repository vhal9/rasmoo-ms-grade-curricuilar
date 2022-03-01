package com.rasmoo.cliente.escola.grade_curricular.models.entitys;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Credencial {

    private String email;

    private String senha;
}
