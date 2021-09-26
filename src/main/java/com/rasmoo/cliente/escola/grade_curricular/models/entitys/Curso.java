package com.rasmoo.cliente.escola.grade_curricular.models.entitys;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_curso")
public class Curso {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", length = 20, nullable = false)
    private String nome;

    @Column(name = "string", length = 5, nullable = false)
    private String codigo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private List<Materia> materias;

}
