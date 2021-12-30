package com.rasmoo.cliente.escola.grade_curricular.controllers;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.CursoNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.EmailExistenteException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.exceptions.SendIdException;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.ResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(MateriaNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<String> handleMateriaNotFoundException(MateriaNotFoundException ex) {

        return createResponseErroWithMessage(ex.getMessage());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<String> erros = ex.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ResponseDTO<List<String>> responseDTO = new ResponseDTO();
        responseDTO.setData(erros);
        responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST.value());

        return responseDTO;

    }

    @ExceptionHandler(SendIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<String> handleSendIdException(SendIdException ex) {

        return createResponseErroWithMessage(ex.getMessage());

    }

    @ExceptionHandler(CursoNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<String> handleCursoNotFoundException(CursoNotFoundException ex) {

        return createResponseErroWithMessage(ex.getMessage());

    }

    @ExceptionHandler(EmailExistenteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<String> handleEmailExistenteException(EmailExistenteException ex) {

        return createResponseErroWithMessage(ex.getMessage());

    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {

        return createResponseErroWithMessage(ex.getMessage());

    }

    private ResponseDTO<String> createResponseErroWithMessage(String message) {

        ResponseDTO<String> responseDTO = new ResponseDTO();
        responseDTO.setData(message);
        responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST.value());

        return responseDTO;

    }


}
