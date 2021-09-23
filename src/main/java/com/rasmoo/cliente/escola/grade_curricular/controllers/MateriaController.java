package com.rasmoo.cliente.escola.grade_curricular.controllers;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MateriaDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.ResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.services.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/materias")
public class MateriaController {

    private MateriaService materiaService;

    @Autowired
    MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @GetMapping
    public ResponseDTO<Page<MateriaDTO>> listMaterias (
            @PageableDefault(sort = "nome",
                    direction = Sort.Direction.ASC,
                    page = 0,
                    size = 5)
                    Pageable pageable) {

        ResponseDTO<Page<MateriaDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setData(materiaService.listMaterias(pageable));
        responseDTO.setStatusCode(HttpStatus.OK.value());

        responseDTO.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(MateriaController.class).listMaterias(pageable))
                .withSelfRel());

        return responseDTO;

    }

    @GetMapping("{id}")
    public ResponseDTO<MateriaDTO> getMateriaById(@PathVariable Long id) throws MateriaNotFoundException {

        ResponseDTO<MateriaDTO> responseDTO = new ResponseDTO<>();
        MateriaDTO materiaDTO = materiaService.getMateriaById(id);
        responseDTO.setData(materiaDTO);
        responseDTO.setStatusCode(HttpStatus.OK.value());

        responseDTO.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(MateriaController.class).getMateriaById(id))
                .withSelfRel());

        responseDTO.add(getwebMvcLinkBuilderUpdate(id, materiaDTO));
        responseDTO.add(getwebMvcLinkBuilderDelete(id));


        return responseDTO;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<MessageResponseDTO> createMateria(@Valid @RequestBody MateriaDTO materiaDTO) throws MateriaNotFoundException {

        ResponseDTO<MessageResponseDTO> responseDTO = new ResponseDTO<>();
        MessageResponseDTO messageResponseDTO = materiaService.createMateria(materiaDTO);
        responseDTO.setData(messageResponseDTO);
        responseDTO.setStatusCode(HttpStatus.CREATED.value());

        responseDTO.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder
                                .methodOn(MateriaController.class)
                                .createMateria(materiaDTO))
                .withSelfRel());

        responseDTO.add(getwebMvcLinkBuilderGet(messageResponseDTO.getId()));
        responseDTO.add(getwebMvcLinkBuilderUpdate(messageResponseDTO.getId(), materiaDTO));
        responseDTO.add(getwebMvcLinkBuilderDelete(messageResponseDTO.getId()));

        return responseDTO;

    }

    @PutMapping("{id}")
    public ResponseDTO<MessageResponseDTO> update(@Valid @PathVariable Long id, @RequestBody MateriaDTO materiaDTO) throws MateriaNotFoundException {

        ResponseDTO<MessageResponseDTO> responseDTO = new ResponseDTO<>();
        MessageResponseDTO messageResponseDTO = materiaService.updateMateria(id, materiaDTO);
        responseDTO.setData(messageResponseDTO);
        responseDTO.setStatusCode(HttpStatus.OK.value());

        responseDTO.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(MateriaController.class).update(id, materiaDTO))
                .withSelfRel());

        responseDTO.add(getwebMvcLinkBuilderGet(id));
        responseDTO.add(getwebMvcLinkBuilderDelete(id));

        return responseDTO;

    }

    @DeleteMapping("{id}")
    public MessageResponseDTO delete(@PathVariable Long id) throws MateriaNotFoundException {

        return materiaService.deleteMateriaById(id);

    }

    private Link getwebMvcLinkBuilderUpdate(Long id, MateriaDTO materiaDTO)
            throws MateriaNotFoundException {

        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder
                    .methodOn(MateriaController.class)
                    .update(id, materiaDTO))
                .withRel("UPDATE");

    }

    private Link getwebMvcLinkBuilderDelete(Long id)
            throws MateriaNotFoundException {

        return WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder
                                .methodOn(MateriaController.class)
                                .delete(id))
                .withRel("DELETE");

    }

    private Link getwebMvcLinkBuilderGet(Long id)
            throws MateriaNotFoundException {

        return WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder
                                .methodOn(MateriaController.class)
                                .getMateriaById(id))
                .withRel("GET");

    }

}
