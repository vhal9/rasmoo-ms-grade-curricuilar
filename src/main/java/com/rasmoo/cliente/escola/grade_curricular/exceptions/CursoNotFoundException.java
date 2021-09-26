package com.rasmoo.cliente.escola.grade_curricular.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CursoNotFoundException extends Exception {

    public CursoNotFoundException(Long id) {
        super(String.format("Curso com o id %s não existe no sistema.", id));
    }
}
