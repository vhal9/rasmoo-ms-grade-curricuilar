package com.rasmoo.cliente.escola.grade_curricular.repositories;

import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
