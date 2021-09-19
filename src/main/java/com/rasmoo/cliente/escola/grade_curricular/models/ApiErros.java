package com.rasmoo.cliente.escola.grade_curricular.models;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class ApiErros {

    private int httpStatus;

    private List<String> erros;

    public ApiErros(List<String> erros, int status) {

        this.erros = erros;
        this.httpStatus = status;

    }

    public ApiErros(String mensagemErro, int status) {

        this.erros = Arrays.asList(mensagemErro);
        this.httpStatus = status;

    }

}
