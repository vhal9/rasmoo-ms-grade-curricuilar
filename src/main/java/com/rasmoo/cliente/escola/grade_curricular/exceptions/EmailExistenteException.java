package com.rasmoo.cliente.escola.grade_curricular.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailExistenteException extends Exception {
    public EmailExistenteException(String email) {
        super(String.format("Email %s utilizado por outro usuario.", email));
    }
}
