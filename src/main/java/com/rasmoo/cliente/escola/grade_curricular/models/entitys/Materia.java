package com.rasmoo.cliente.escola.grade_curricular.models.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_materia")
@Data
@NoArgsConstructor
public class Materia implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", length = 30, nullable = false)
    private String nome;

    @Column(name = "horas", nullable = false)
    private int horas;

    @Column(name = "codigo", length = 10, nullable = false)
    private String codigo;

    @ManyToOne
    @JsonIgnore
    private Usuario usuario;

}
