package com.FordCare.API.veiculo;

import com.FordCare.API.veiculo.veiculoDto.VeiculoResponseDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findByUsuarioId(Long usuarioId);
    void deleteByUsuarioId(Long usuarioId);
    boolean existsByUsuarioId(Long usuarioId);
}
