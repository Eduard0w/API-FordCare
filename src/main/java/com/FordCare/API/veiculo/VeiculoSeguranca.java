package com.FordCare.API.veiculo;

import com.FordCare.API.usuario.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.file.AccessDeniedException;

public class VeiculoSeguranca {

    boolean validarUsuario(Veiculo veiculo) throws AccessDeniedException {
        Usuario usuarioDono = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!veiculo.getUsuario().getId().equals(usuarioDono.getId())){
            return false;
        }
        return true;
    }
}
