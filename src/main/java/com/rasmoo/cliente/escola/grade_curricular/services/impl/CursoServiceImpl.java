package com.rasmoo.cliente.escola.grade_curricular.services.impl;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.CursoNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.SendIdException;
import com.rasmoo.cliente.escola.grade_curricular.mappers.CursoDTOMapper;
import com.rasmoo.cliente.escola.grade_curricular.mappers.CursoMapper;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.CursoDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Curso;
import com.rasmoo.cliente.escola.grade_curricular.repositories.CursoRepository;
import com.rasmoo.cliente.escola.grade_curricular.services.CursoService;
import com.rasmoo.cliente.escola.grade_curricular.services.MateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;
    private final CursoDTOMapper cursoDTOMapper;
    private final MateriaService materiaService;

    @Override
    public Page<CursoDTO> listAll(Pageable pageable) {

        Page<Curso> cursosPage = cursoRepository.findAll(pageable);
        return cursosPage.map(cursoDTOMapper::execute);

    }

    @Override
    public Curso findCursoById(Long id) throws CursoNotFoundException {

        return cursoRepository.findById(id)
                .orElseThrow(() -> new CursoNotFoundException(id));

    }

    @Override
    public MessageResponseDTO createCurso(CursoDTO cursoDTO) throws SendIdException, MateriaNotFoundException {

        if (cursoDTO.getId() != null)
            throw new SendIdException("curso");

        verificaSeMateriasExistem(cursoDTO.getMaterias());

        Curso cursoToSave = cursoMapper.execute(cursoDTO);

        Curso savedCurso = cursoRepository.save(cursoToSave);

        return createdMessageResponse(savedCurso.getId(), "Criado curso com ID ");

    }

    @Override
    public MessageResponseDTO updateCurso(Long id, CursoDTO cursoDTO) throws CursoNotFoundException, MateriaNotFoundException {

        findCursoById(id);
        verificaSeMateriasExistem(cursoDTO.getMaterias());

        Curso cursoToSave = cursoMapper.execute(cursoDTO);
        cursoToSave.setId(id);
        Curso savedCurso = cursoRepository.save(cursoToSave);

        return createdMessageResponse(savedCurso.getId(), "Alterado curso com ID ");

    }

    @Override
    public MessageResponseDTO deleteCurso(Long id) throws CursoNotFoundException {

        Curso curso = findCursoById(id);
        cursoRepository.delete(curso);

        return createdMessageResponse(id, "Deletado curso com ID ");

    }

    private MessageResponseDTO createdMessageResponse(Long id, String message) {

        return MessageResponseDTO
                .builder()
                .id(id)
                .message(message + id)
                .build();

    }

    private void verificaSeMateriasExistem(List<MateriaDTO> materiasDTO) throws MateriaNotFoundException {
        if (!materiasDTO.isEmpty()) {
            for (MateriaDTO materia : materiasDTO) {
                materiaService.getMateriaById(materia.getId());
            }
        }
    }

}
