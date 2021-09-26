package com.rasmoo.cliente.escola.grade_curricular.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SendIdException extends Exception{

    public SendIdException(String objeto) {
        super("O id não pode ser enviado ao cadastrar " + objeto);
    }

}
