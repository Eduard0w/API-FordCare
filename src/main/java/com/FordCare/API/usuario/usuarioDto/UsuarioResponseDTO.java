package com.FordCare.API.usuario.usuarioDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {
    private Long id;
    private String nome;

    public UsuarioResponseDTO (Long id, String nome){
        this.id = id;
        this.nome = nome;
    }
}
