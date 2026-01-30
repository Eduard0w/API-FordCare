package com.FordCare.API.usuario;

import com.FordCare.API.usuario.usuarioDto.LoginDTO;
import com.FordCare.API.usuario.usuarioDto.UsuarioDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario criarUsuario(@NotNull UsuarioDTO dados){
        Usuario usuario = new Usuario();
        usuario.setNome(dados.getNome());
        usuario.setEmail(dados.getEmail().toLowerCase());
//        String senhaCriptada = new BCryptPasswordEncoder().encode(dados.getSenha());
        usuario.setSenha(passwordEncoder.encode(dados.getSenha()));

        return repository.save(usuario);
    }
}
