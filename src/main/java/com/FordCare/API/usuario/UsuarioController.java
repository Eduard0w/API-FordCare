package com.FordCare.API.usuario;

import com.FordCare.API.usuario.usuarioDto.LoginDTO;
import com.FordCare.API.usuario.usuarioDto.UsuarioDTO;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/login")
    public ResponseEntity<Boolean> efetuarLogin(@RequestBody LoginDTO login){
        boolean usuarioExiste = service.verificarUsuario(login);

        if (usuarioExiste) {
            return ResponseEntity.ok(true);
        } else {
            // Retorna 401 (Unauthorized) ou 403 (Forbidden) se a senha estiver errada
            return ResponseEntity.status(401).body(false);
        }
    }

    @PostMapping("/criar")
    public ResponseEntity<@NotNull Usuario> criarUsuario(@RequestBody UsuarioDTO novoUsuario, @NotNull UriComponentsBuilder uriBuilder){
        Usuario usuarioSalvo = service.criarUsuario(novoUsuario);

        //Para seguir o padrão REST é necessario dizer ao cliente onde esse algo está
        URI uri = uriBuilder.path("/usuario/{id}").buildAndExpand(usuarioSalvo.getId()).toUri();

        //retorna 201 se for crido com sucesso
        return ResponseEntity.created(uri).body(usuarioSalvo);
    }
}
