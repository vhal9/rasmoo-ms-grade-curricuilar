package com.rasmoo.cliente.escola.grade_curricular.controllers;

import com.rasmoo.cliente.escola.grade_curricular.exceptions.MateriaNotFoundException;
import com.rasmoo.cliente.escola.grade_curricular.models.ApiErros;
import com.rasmoo.cliente.escola.grade_curricular.models.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
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

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(ex.getMessage());
        responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST.value());

        return responseDTO;

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<String> erros = ex.getBindingResult().getAllErrors()
                .stream()
                .map( erro -> erro.getDefaultMessage())
                .collect(Collectors.toList());

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(erros);
        responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST.value());

        return responseDTO;

    }
}
