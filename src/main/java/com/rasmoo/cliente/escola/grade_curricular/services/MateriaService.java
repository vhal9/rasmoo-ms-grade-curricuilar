package com.rasmoo.cliente.escola.grade_curricular.services;

import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import com.rasmoo.cliente.escola.grade_curricular.repositories.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MateriaService {

    private MateriaRepository materiaRepository;

    @Autowired
    MateriaService (MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public Page<Materia> listMaterias(Pageable pageable) {

        return this.materiaRepository.findAll(pageable);

    }

}
