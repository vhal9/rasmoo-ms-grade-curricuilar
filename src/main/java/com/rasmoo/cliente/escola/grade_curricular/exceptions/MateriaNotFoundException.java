package com.rasmoo.cliente.escola.grade_curricular.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MateriaNotFoundException extends Exception {

    public MateriaNotFoundException(Long id) {
        super(String.format("Matéria com ID %s não encontrada no sistema.", id));
    }

}
