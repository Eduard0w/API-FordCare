package com.FordCare.API.usuario;

import com.FordCare.API.security.TokenService;
import com.FordCare.API.usuario.usuarioDto.LoginDTO;
import com.FordCare.API.usuario.usuarioDto.LoginResponseDTO;
import com.FordCare.API.usuario.usuarioDto.UsuarioDTO;
import jakarta.validation.Valid;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

//    @PostMapping("/login")
//    public ResponseEntity<Boolean> efetuarLogin(@RequestBody LoginDTO login){
//        boolean usuarioExiste = service.verificarUsuario(login);
//
//        if (usuarioExiste) {
//            return ResponseEntity.ok(true);
//        } else {
//            // Retorna 401 (Unauthorized) ou 403 (Forbidden) se a senha estiver errada
//            return ResponseEntity.status(401).body(false);
//        }
//    }

    //Para efetuar login precisaria de alguma autorização? Ou deve ser permitido por qualquer um?
    // Acho que deve ser permitido para qualquer um a principio.
    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody @Valid @NotNull LoginDTO login){
        var userNamePassword = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getSenha());
        var auth = authenticationManager.authenticate(userNamePassword);

        var token = tokenService.geradorToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity criarUsuario(@RequestBody @NotNull @Valid UsuarioDTO usuario, @NotNull UriComponentsBuilder uriBuilder){
        if(repository.existsByEmail(usuario.getEmail())) return ResponseEntity.badRequest().build();

        Usuario novoUsuario = service.criarUsuario(usuario);

        //Para seguir o padrão REST é necessario dizer ao cliente onde esse algo está
        URI uri = uriBuilder.path("/usuario/{id}").buildAndExpand(novoUsuario.getId()).toUri();

        //retorna 201 se for crido com sucesso
        return ResponseEntity.created(uri).build();
    }
}
