package com.FordCare.API.usuario;

import com.FordCare.API.usuario.usuarioDto.LoginDTO;
import com.FordCare.API.usuario.usuarioDto.UsuarioDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario criarUsuario(@NotNull UsuarioDTO dados){
        Usuario usuario = new Usuario();
        usuario.setNome(dados.getNome());
        usuario.setEmail(dados.getEmail().toLowerCase());
        usuario.setSenha(dados.getSenha());

        return repository.save(usuario);
    }

    public boolean verificarUsuario(@NotNull LoginDTO login) {
       Optional<Usuario> usuarioEncontrado =
               repository.findByEmailAndSenha(login.getEmail().toLowerCase(), login.getSenha());

       return usuarioEncontrado.isPresent();
    }
}
