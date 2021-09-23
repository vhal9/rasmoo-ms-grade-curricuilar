package com.rasmoo.cliente.escola.grade_curricular.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResponseDTO<T> extends RepresentationModel<ResponseDTO<T>> {

    private int statusCode;

    private T data;


}
