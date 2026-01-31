package com.FordCare.API.veiculo;

import com.FordCare.API.usuario.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
public class VeiculoInfra {

    boolean validarUsuario(Veiculo veiculo) throws AccessDeniedException {
        Usuario usuarioDono = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!veiculo.getUsuario().getId().equals(usuarioDono.getId())){
            return false;
        }
        return true;
    }

    public Usuario getUsuarioLogado(){
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
