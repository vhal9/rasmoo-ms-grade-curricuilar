package com.rasmoo.cliente.escola.grade_curricular.services;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.CursoNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.SendIdException;
import com.rasmoo.cliente.escola.grade_curricular.mappers.CursoMapper;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.CursoDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.ResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Curso;
import com.rasmoo.cliente.escola.grade_curricular.repositories.CursoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CursoService {

    private CursoRepository cursoRepository;

    private MateriaService materiaService;

    private final CursoMapper cursoMapper = CursoMapper.INSTANCE;

    public Page<Curso> listAll(Pageable pageable) {

        return cursoRepository.findAll(pageable);

    }

    public Curso findCursoById(Long id) throws CursoNotFoundException {

        return cursoRepository.findById(id)
                .orElseThrow(() -> new CursoNotFoundException(id));

    }


    public MessageResponseDTO createCurso(CursoDTO cursoDTO) throws MateriaNotFoundException, SendIdException {

        if (cursoDTO.getId() != null)
            throw new SendIdException("curso");

        Curso cursoToSave = cursoMapper.toModel(cursoDTO);

        cursoToSave.setMaterias(materiaService.findMateriasByIds(cursoDTO.getIdsMaterias()));

        Curso savedCurso = cursoRepository.save(cursoToSave);

        return createdMessageResponse(savedCurso.getId(), "Created curso with ID ");

    }

    private MessageResponseDTO createdMessageResponse(Long id, String message) {

        return MessageResponseDTO
                .builder()
                .id(id)
                .message(message + id)
                .build();

    }



}
