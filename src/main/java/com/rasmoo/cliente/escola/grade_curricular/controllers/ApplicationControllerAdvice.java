package com.rasmoo.cliente.escola.grade_curricular.controllers;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.CursoNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.EmailExistenteException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.SendIdException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.ResponseDTO;
import com.rasmoo.cliente.escola.grade_curricular.services.CreateResponseErroService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ApplicationControllerAdvice extends CreateResponseErroService {

    @ExceptionHandler(MateriaNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<String> handleMateriaNotFoundException(MateriaNotFoundException ex) {

        return createResponseErroWithMessageAndBadRequestStatus(ex.getMessage());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        return createResponseErroWithListMessageAndBadRequestStatus(ex);


    }

    @ExceptionHandler(SendIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<String> handleSendIdException(SendIdException ex) {

        return createResponseErroWithMessageAndBadRequestStatus(ex.getMessage());

    }

    @ExceptionHandler(CursoNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<String> handleCursoNotFoundException(CursoNotFoundException ex) {

        return createResponseErroWithMessageAndBadRequestStatus(ex.getMessage());

    }

    @ExceptionHandler(EmailExistenteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<String> handleEmailExistenteException(EmailExistenteException ex) {

        return createResponseErroWithMessageAndBadRequestStatus(ex.getMessage());

    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {

        return createResponseErroWithMessageAndBadRequestStatus(ex.getMessage());

    }

}
