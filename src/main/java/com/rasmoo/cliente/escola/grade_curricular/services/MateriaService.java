package com.rasmoo.cliente.escola.grade_curricular.services;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.mappers.MateriaDTOMapper;
import com.rasmoo.cliente.escola.grade_curricular.mappers.MateriaMapper;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import com.rasmoo.cliente.escola.grade_curricular.repositories.MateriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MateriaService {

    private MateriaRepository materiaRepository;

    private MateriaMapper materiaMapper;

    private MateriaDTOMapper materiaDTOMapper;

    public List<MateriaDTO> listMaterias() {

        List<Materia> allMaterias = materiaRepository.findAll();

        return allMaterias.stream().map(materiaDTOMapper::execute).collect(Collectors.toList());

    }

    public MateriaDTO getMateriaById(Long id) throws MateriaNotFoundException {

        Materia materia = findMateriaById(id);
        return materiaDTOMapper.execute(materia);

    }

    public MessageResponseDTO createMateria(MateriaDTO materiaDTO) {

        Materia materiaToSave = materiaMapper.execute(materiaDTO);
        Materia savedMateria = materiaRepository.save(materiaToSave);

        return createdMessageResponse(savedMateria.getId(), "Created Materia with ID ");
    }

    public MessageResponseDTO updateMateria(Long id, MateriaDTO materiaDTO) throws
            MateriaNotFoundException {

        Materia materia = findMateriaById(id);

        materiaDTO.setId(materia.getId());
        Materia materiaToSave = materiaMapper.execute(materiaDTO);
        Materia savedMateria = materiaRepository.save(materiaToSave);

        return createdMessageResponse(savedMateria.getId(), "Updated Materia with ID ");

    }

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
}
