package com.rasmoo.cliente.escola.grade_curricular.repositories;

import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
}
