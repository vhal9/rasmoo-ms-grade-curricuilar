package com.rasmoo.cliente.escola.grade_curricular.models.entitys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_curso")
public class Curso implements Serializable {

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
    @JoinTable(name = "tb_curso_materia",
            joinColumns = @JoinColumn(name = "tb_materia_id"),
            inverseJoinColumns = @JoinColumn(name = "tb_curso_id"))
    private List<Materia> materias;

}
