package com.rasmoo.cliente.escola.grade_curricular.models.dto;


import lombok.Data;

@Data
public class ResponseDTO<T> {

    private int httpStatus;
    private T data;
    private Long timestamp;

    public ResponseDTO(){
        this.timestamp = System.currentTimeMillis();
    }

}
