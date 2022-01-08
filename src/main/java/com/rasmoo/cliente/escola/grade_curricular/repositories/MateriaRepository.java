package com.rasmoo.cliente.escola.grade_curricular.repositories;

import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
    @Query("select m from Materia m where m.usuario.id = :userId")
    List<Materia> findAllByUsuarioId(@Param("userId") Long id);
}
