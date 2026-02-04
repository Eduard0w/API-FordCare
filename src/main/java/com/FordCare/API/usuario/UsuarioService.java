package com.FordCare.API.usuario;

import com.FordCare.API.usuario.usuarioDto.LoginDTO;
import com.FordCare.API.usuario.usuarioDto.UsuarioDTO;
import com.FordCare.API.veiculo.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private VeiculoRepository veiculoRepository;

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

    @Transactional
    public void deletarUsuario(){
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(veiculoRepository.existsByUsuarioId(usuario.getId())){
            veiculoRepository.deleteByUsuarioId(usuario.getId());
        }
        repository.deleteById(usuario.getId());
    }
}
