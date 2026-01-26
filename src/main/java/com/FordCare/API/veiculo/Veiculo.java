package com.FordCare.API.veiculo;

import com.FordCare.API.model.RegistroManutencao;
import com.FordCare.API.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "veiculos")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private Integer anoVeiculo;
    private String placa;
    private String tipoCombustivel;
    private Integer km;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "data", column = @Column(name = "ult_troca_oleo_data")),
            @AttributeOverride(name = "km", column = @Column(name = "ult_troca_oleo_km"))
    })
    private RegistroManutencao ultTrocaOleo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "data", column = @Column(name = "ult_troca_filtro_data")),
            @AttributeOverride(name = "km", column = @Column(name = "ult_troca_filtro_km"))
    })
    private RegistroManutencao ultTrocaFiltro;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "data", column = @Column(name = "ult_troca_pastilhas_data")),
            @AttributeOverride(name = "km", column = @Column(name = "ult_troca_pastilhas_km"))
    })
    private RegistroManutencao ultTrocaPastilhas;

    @ElementCollection // JPA cria uma tabela extra automaticamente para essa lista
    @CollectionTable(name = "veiculo_alertas", joinColumns = @JoinColumn(name = "veiculo_id"))
    @Column(name = "alerta")

    private List<String> alertaPainel;

    private String tipoUso;
    private Integer kmMedio;
    private String imagemVeiculo;
    private Integer saudeVeiculo;
}
