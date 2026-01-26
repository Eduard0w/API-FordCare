package com.FordCare.API.veiculo.veiculoDto;

import com.FordCare.API.model.RegistroManutencao;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VeiculoDTO {
    private String marca;
    private String modelo;
    private Integer anoVeiculo;
    private String placa;
    private String tipoCombustivel;
    private Integer km;
    private RegistroManutencao ultTrocaOleo;
    private RegistroManutencao ultTrocaFiltro;
    private RegistroManutencao ultTrocaPastilhas;
    private List<String> alertaPainel;
    private String tipoUso;
    private Integer kmMedio;
    private Integer saudeVeiculo;
    private String imageVeiculo;

    private Long usuarioId;
}
