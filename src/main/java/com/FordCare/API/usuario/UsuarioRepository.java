package com.FordCare.API.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Isso aqui está errado. Não é certo a busca ser por email e senha.
    //A senha deve ser escondida e não amostra.
    //Optional<Usuario> findByEmailAndSenha(String email, String senha);

    UserDetails findByEmail(String email);
}