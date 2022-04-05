package com.rasmoo.cliente.escola.grade_curricular.services;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.UserNotAuthorizeException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.UserNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.mappers.MateriaDTOMapper;
import com.rasmoo.cliente.escola.grade_curricular.mappers.MateriaMapper;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Materia;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Usuario;
import com.rasmoo.cliente.escola.grade_curricular.repositories.MateriaRepository;
import com.rasmoo.cliente.escola.grade_curricular.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MateriaService {

    private MateriaRepository materiaRepository;

    private MateriaMapper materiaMapper;

    private MateriaDTOMapper materiaDTOMapper;

    private UsuarioRepository usuarioRepository;

    public List<MateriaDTO> listMaterias() throws UserNotFoundException {

        Usuario usuario = this.findAuthUser();
        List<Materia> allMaterias = materiaRepository.findAllByUsuarioId(usuario.getId());

        return allMaterias.stream().map(materiaDTOMapper::execute).collect(Collectors.toList());

    }

    public MateriaDTO getMateriaById(Long id) throws MateriaNotFoundException, UserNotFoundException, UserNotAuthorizeException {

        Usuario usuario = this.findAuthUser();

        Materia materia = findMateriaById(id);

        if (materia.getUsuario().equals(usuario)) {

            return materiaDTOMapper.execute(materia);

        } else {
            throw new UserNotAuthorizeException(usuario.getCredencial().getEmail());
        }
    }

    public MessageResponseDTO createMateria(MateriaDTO materiaDTO) throws UserNotFoundException {

        Usuario usuario = this.findAuthUser();

        Materia materiaToSave = materiaMapper.execute(materiaDTO);
        materiaToSave.setUsuario(usuario);
        Materia savedMateria = materiaRepository.save(materiaToSave);

        return createdMessageResponse(savedMateria.getId(), "Created Materia with ID ");
    }

    public MessageResponseDTO updateMateria(Long id, MateriaDTO materiaDTO) throws UserNotFoundException,
            MateriaNotFoundException, UserNotAuthorizeException {

        Usuario usuario = this.findAuthUser();
        Materia materia = findMateriaById(id);

        if (materia.getUsuario().equals(usuario)) {

            materiaDTO.setId(id);
            Materia materiaToSave = materiaMapper.execute(materiaDTO);
            materiaToSave.setUsuario(usuario);
            Materia savedMateria = materiaRepository.save(materiaToSave);

            return createdMessageResponse(savedMateria.getId(), "Updated Materia with ID ");

        } else {
            throw new UserNotAuthorizeException(usuario.getCredencial().getEmail());
        }

    }

    public MessageResponseDTO deleteMateriaById(Long id) throws UserNotFoundException,
            MateriaNotFoundException, UserNotAuthorizeException {

        Usuario usuario = this.findAuthUser();
        Materia materia = findMateriaById(id);

        if (materia.getUsuario().equals(usuario)) {

            materiaRepository.deleteById(id);
            return createdMessageResponse(id, "Deleted Materia with ID ");

        } else {

            throw new UserNotAuthorizeException(usuario.getCredencial().getEmail());

        }

    }

    public List<Materia> findMateriasByIds(List<Long> ids) throws MateriaNotFoundException {

        List<Materia> materias = new ArrayList<>();

        for (Long id : ids) {

            materias.add(this.findMateriaById(id));

        }

        return materias;

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

    private Usuario findAuthUser() throws UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<Usuario> user = usuarioRepository.findByCredencial_Email(auth.getName());

        if (!user.isPresent()) {
            throw new UserNotFoundException(auth.getName());
        }
        return user.get();
    }
}
