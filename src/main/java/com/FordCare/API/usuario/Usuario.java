package com.FordCare.API.usuario;

import com.FordCare.API.veiculo.Veiculo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/*@Getter
@Setter
@AllArgsConstructor Cria constructor com todos os parementros*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity //Define uma entidade para o banco de dados
//@ToString
@Table(name="usuarios")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(min = 8)
    private String senha;

    // --- CORREÇÃO AQUI ---
    @OneToMany(mappedBy = "usuario") // "usuario" é o nome do atributo lá na classe Veiculo
    @JsonIgnore // Evita o loop infinito quando transformar em JSON
    @ToString.Exclude // Evita loop infinito no System.out.println
    private List<Veiculo> veiculos;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return getSenha();
    }

    @Override
    public String getUsername() {
        return getNome();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
