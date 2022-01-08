package com.rasmoo.cliente.escola.grade_curricular.services;

import com.rasmoo.cliente.escola.grade_curricular.models.dto.ResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;

import java.util.List;
import java.util.stream.Collectors;

public class CreateResponseErroService {

    public ResponseDTO<String> createResponseErroWithMessageAndBadRequestStatus(String message) {

        return createResponseErroWithMessage(message, HttpStatus.BAD_REQUEST);

    }

    public ResponseDTO<String> createResponseErroWithMessageAndUnauthorizedStatus(String message) {

        return createResponseErroWithMessage(message, HttpStatus.UNAUTHORIZED);

    }

    public ResponseDTO<List<String>> createResponseErroWithListMessageAndBadRequestStatus(BindException allErros) {

        return createResponseErroWithListMessage(allErros, HttpStatus.BAD_REQUEST);

    }

    public ResponseDTO<String> createResponseErroWithMessage(String message, HttpStatus status) {

        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.setData(message);
        responseDTO.setHttpStatus(status.value());

        return responseDTO;

    }

    public ResponseDTO<List<String>> createResponseErroWithListMessage(BindException allErros, HttpStatus status) {

        List<String> erros = allErros.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ResponseDTO<List<String>> responseDTO = new ResponseDTO<>();
        responseDTO.setData(erros);
        responseDTO.setHttpStatus(status.value());
        return responseDTO;

    }
}
