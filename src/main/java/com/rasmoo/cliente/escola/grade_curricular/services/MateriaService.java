package com.rasmoo.cliente.escola.grade_curricular.services;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;

import java.util.List;

public interface MateriaService {

    public List<MateriaDTO> listMaterias();

    public MateriaDTO getMateriaById(Long id) throws MateriaNotFoundException;

    public MessageResponseDTO createMateria(MateriaDTO materiaDTO);

    public MessageResponseDTO updateMateria(Long id, MateriaDTO materiaDTO) throws MateriaNotFoundException;

    public MessageResponseDTO deleteMateriaById(Long id) throws MateriaNotFoundException;
}
