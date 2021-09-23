package com.rasmoo.cliente.escola.grade_curricular.services;

import com.rasmoo.cliente.escola.grade_curricular.controllers.MateriaController;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.mappers.MateriaMapper;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import com.rasmoo.cliente.escola.grade_curricular.repositories.MateriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "materia")
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MateriaService {

    private MateriaRepository materiaRepository;

    private final MateriaMapper materiaMapper = MateriaMapper.INSTANCE;

    @CachePut(unless = "#result.getTotalElements() < 3")
    public Page<MateriaDTO> listMaterias(Pageable pageable) {

        Page<Materia> allMaterias = materiaRepository.findAll(pageable);

        return allMaterias.map(materia -> {
            try {
                return materiaMapper.toDTO(materia).add(
                        WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(MateriaController.class)
                                        .getMateriaById(materia.getId())).withSelfRel()
                );
            } catch (MateriaNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        });

    }
    
    @CachePut(key = "#id")
    public MateriaDTO getMateriaById(Long id) throws MateriaNotFoundException {

        Materia materia = findMateriaById(id);

        return materiaMapper.toDTO(materia);

    }

    public MessageResponseDTO createMateria(MateriaDTO materiaDTO) {

        Materia materiaToSave = materiaMapper.toModel(materiaDTO);
        Materia savedMateria = materiaRepository.save(materiaToSave);

        return createdMessageResponse(savedMateria.getId(), "Created Materia with ID ");
    }

    public MessageResponseDTO updateMateria(Long id, MateriaDTO materiaDTO) throws MateriaNotFoundException {

        verifyIfMateriaExists(id);

        materiaDTO.setId(id);
        Materia materiaToSave = materiaMapper.toModel(materiaDTO);
        Materia savedMateria = materiaRepository.save(materiaToSave);

        return createdMessageResponse(savedMateria.getId(), "Update Materia with ID ");

    }

    public MessageResponseDTO deleteMateriaById(Long id) throws MateriaNotFoundException {

        verifyIfMateriaExists(id);

        materiaRepository.deleteById(id);

        return createdMessageResponse(id, "Delete Materia with ID ");

    }

    private MessageResponseDTO createdMessageResponse(Long id, String message) {

        return MessageResponseDTO
                .builder()
                .id(id)
                .message(message + id)
                .build();

    }

    private void verifyIfMateriaExists(Long id) throws MateriaNotFoundException {

        findMateriaById(id);

    }

    private Materia findMateriaById(Long id) throws MateriaNotFoundException {

        return materiaRepository.findById(id)
                .orElseThrow(()-> new MateriaNotFoundException(id));

    }

}
