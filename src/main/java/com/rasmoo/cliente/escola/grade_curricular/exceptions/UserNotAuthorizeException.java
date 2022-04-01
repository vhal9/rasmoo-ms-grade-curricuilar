package com.rasmoo.cliente.escola.grade_curricular.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserNotAuthorizeException extends Exception {

    public UserNotAuthorizeException(String email) {
        super(String.format("Usuário com email %s não tem autorização ao recurso.", email));
    }

}
