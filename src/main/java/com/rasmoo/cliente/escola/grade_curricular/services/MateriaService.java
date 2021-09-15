package com.rasmoo.cliente.escola.grade_curricular.services;

import com.rasmoo.cliente.escola.grade_curricular.mappers.MateriaMapper;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import com.rasmoo.cliente.escola.grade_curricular.repositories.MateriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MateriaService {

    private MateriaRepository materiaRepository;

    private final MateriaMapper materiaMapper = MateriaMapper.INSTANCE;

    public Page<MateriaDTO> listMaterias(Pageable pageable) {

        Page<Materia> allMaterias = materiaRepository.findAll(pageable);

        return allMaterias.map(materia -> materiaMapper.toDTO(materia));

    }

}
