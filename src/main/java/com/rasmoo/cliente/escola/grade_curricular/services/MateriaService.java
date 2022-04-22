package com.rasmoo.cliente.escola.grade_curricular.services;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.SendIdException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;

import java.util.List;

public interface MateriaService {

    List<MateriaDTO> listMaterias();
    MateriaDTO getMateriaById(Long id) throws MateriaNotFoundException;
    MessageResponseDTO createMateria(MateriaDTO materiaDTO) throws SendIdException;
    MessageResponseDTO updateMateria(Long id, MateriaDTO materiaDTO) throws MateriaNotFoundException;
    MessageResponseDTO deleteMateriaById(Long id) throws MateriaNotFoundException;
}
