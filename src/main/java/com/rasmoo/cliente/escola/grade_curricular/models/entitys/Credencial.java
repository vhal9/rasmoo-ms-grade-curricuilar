package com.rasmoo.cliente.escola.grade_curricular.models.entitys;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class Credencial implements Serializable {

    private String email;

    private String senha;
}
