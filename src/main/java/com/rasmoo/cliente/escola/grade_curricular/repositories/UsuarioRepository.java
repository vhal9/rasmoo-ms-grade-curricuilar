package com.rasmoo.cliente.escola.grade_curricular.repositories;

import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCredencial_Email(String email);

}
