package com.rasmoo.cliente.escola.grade_curricular.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MateriaNotFoundException extends Exception {

    public MateriaNotFoundException(Long id) {
        super(String.format("Subject with ID %s not found in the system.", id));
    }

}
