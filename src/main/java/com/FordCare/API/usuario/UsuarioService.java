package com.FordCare.API.usuario;

import com.FordCare.API.usuario.usuarioDto.LoginDTO;
import com.FordCare.API.usuario.usuarioDto.UsuarioDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario criarUsuario(@NotNull String nome, String email, String senha){
        Usuario usuario = new Usuario();
//        usuario.setNome(dados.getNome());
//        usuario.setEmail(dados.getEmail().toLowerCase());
//        usuario.setSenha(dados.getSenha());
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        return repository.save(usuario);
    }

//    public boolean verificarUsuario(@NotNull LoginDTO login) {
////       UserDetails usuarioEncontrado =
////               repository.findByEmail(login.getEmail().toLowerCase());
////
////       return usuarioEncontrado.isPresent();
//    }
}
