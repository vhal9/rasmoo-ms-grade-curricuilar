package com.rasmoo.cliente.escola.grade_curricular.builders;

import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Credencial;
import com.rasmoo.cliente.escola.grade_curricular.models.entitys.Usuario;
import lombok.Builder;

@Builder
public class UsuarioBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String nome = "teste";

    @Builder.Default
    private String role = "ROLE_CUSTOMER";

    @Builder.Default
    private Credencial credencial = new Credencial();

    public Usuario toUsuario() {
        credencial.setEmail("teste@teste.com");
        credencial.setSenha("1111");
        return new Usuario(id,
                nome,
                role,
                credencial);
    }
}
