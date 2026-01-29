package com.FordCare.API.usuario.usuarioDto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String Token;

    public LoginResponseDTO(String token){
        this.Token = token;
    }
}
