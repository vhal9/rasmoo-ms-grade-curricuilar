package com.rasmoo.cliente.escola.grade_curricular.services.impl;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.SendIdException;
import com.rasmoo.cliente.escola.grade_curricular.mappers.MateriaDTOMapper;
import com.rasmoo.cliente.escola.grade_curricular.mappers.MateriaMapper;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import com.rasmoo.cliente.escola.grade_curricular.repositories.MateriaRepository;
import com.rasmoo.cliente.escola.grade_curricular.services.MateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MateriaServiceImpl implements MateriaService {

    private final MateriaRepository materiaRepository;
    private final MateriaMapper materiaMapper;
    private final MateriaDTOMapper materiaDTOMapper;

    @Override
    public List<MateriaDTO> listMaterias() {

        List<Materia> allMaterias = materiaRepository.findAll();

        return allMaterias.stream().map(materiaDTOMapper::execute).collect(Collectors.toList());

    }

    @Override
    public MateriaDTO getMateriaById(Long id) throws MateriaNotFoundException {

        Materia materia = findMateriaById(id);
        return materiaDTOMapper.execute(materia);

    }

    @Override
    public MessageResponseDTO createMateria(MateriaDTO materiaDTO) throws SendIdException {

        checksIfIdWasSent(materiaDTO);

        Materia materiaToSave = materiaMapper.execute(materiaDTO);
        Materia savedMateria = materiaRepository.save(materiaToSave);

        return createdMessageResponse(savedMateria.getId(), "Created Materia with ID ");
    }

    @Override
    public MessageResponseDTO updateMateria(Long id, MateriaDTO materiaDTO) throws
            MateriaNotFoundException {

        Materia materia = findMateriaById(id);

        materiaDTO.setId(materia.getId());
        Materia materiaToSave = materiaMapper.execute(materiaDTO);
        Materia savedMateria = materiaRepository.save(materiaToSave);

        return createdMessageResponse(savedMateria.getId(), "Updated Materia with ID ");

    }

    @Override
    public MessageResponseDTO deleteMateriaById(Long id) throws
            MateriaNotFoundException {

        //TODO: verificar se a materia esta associada a algum curso
        Materia materia = findMateriaById(id);

        materiaRepository.delete(materia);
        return createdMessageResponse(id, "Deleted Materia with ID ");

    }

    private MessageResponseDTO createdMessageResponse(Long id, String message) {

        return MessageResponseDTO
                .builder()
                .id(id)
                .message(message + id)
                .build();

    }

    private Materia findMateriaById(Long id) throws MateriaNotFoundException {

        return materiaRepository.findById(id)
                .orElseThrow(()-> new MateriaNotFoundException(id));

    }

    private void checksIfIdWasSent(MateriaDTO materiaDTO) throws SendIdException {
        if(Objects.nonNull(materiaDTO.getId()))
            throw new SendIdException("materia");
    }
}
