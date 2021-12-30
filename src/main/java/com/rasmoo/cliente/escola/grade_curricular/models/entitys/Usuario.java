package com.rasmoo.cliente.escola.grade_curricular.models.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_user")
public class Usuario {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @JsonIgnore
    @Column(name = "credencial")
    private Credencial credencial;

}
