package com.rasmoo.cliente.escola.grade_curricular.builders;

import com.rasmoo.cliente.escola.grade_curricular.models.dto.MessageResponseDTO;
import lombok.Builder;

@Builder
public class MateriaMensagemResponseDTO {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String responsePost = "Created Materia with ID 1";

    @Builder.Default
    private String responsePut = "Updated Materia with ID 1";

    @Builder.Default
    private String responseDelete = "Deleted Materia with ID 1";

    public MessageResponseDTO toResponsePost() {
        return MessageResponseDTO
                .builder()
                .id(id)
                .message(responsePost + id)
                .build();
    }

    public MessageResponseDTO toResponsePut() {
        return MessageResponseDTO
                .builder()
                .id(id)
                .message(responsePut + id)
                .build();
    }

    public MessageResponseDTO toResponseDelete() {
        return MessageResponseDTO
                .builder()
                .id(id)
                .message(responseDelete + id)
                .build();
    }
}
