package com.FordCare.API.usuario;

import com.FordCare.API.veiculo.Veiculo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/*@Getter
@Setter
@AllArgsConstructor Cria constructor com todos os parementros*/
@Data
@Entity //Define uma entidade para o banco de dados
@ToString
@Table(name="usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha;

    // --- CORREÇÃO AQUI ---
    @OneToMany(mappedBy = "usuario") // "usuario" é o nome do atributo lá na classe Veiculo
    @JsonIgnore // Evita o loop infinito quando transformar em JSON
    @ToString.Exclude // Evita loop infinito no System.out.println
    private List<Veiculo> veiculos;


}
