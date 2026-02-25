package com.FordCare.API;

import com.FordCare.API.model.RegistroManutencao;
import com.FordCare.API.usuario.Usuario;
import com.FordCare.API.usuario.UsuarioRepository;
import com.FordCare.API.veiculo.Veiculo;
import com.FordCare.API.veiculo.VeiculoRepository;
import com.FordCare.API.veiculo.VeiculoService;
import com.FordCare.API.veiculo.veiculoDto.VeiculoDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita o Mockito no JUnit 5
public class VeiculoServiceTest {

    //Só mockamos as dependências, pois elas são necessárias para o teste,
    // ou seja, só "falsificamos" aquilo necessário para o funcionamento da classe que queremos testar.
    @InjectMocks
    private VeiculoService veiculoService;
    @Mock
    private VeiculoRepository veiculoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;

//    @Test
//    void cadastrarVeiculoComSucessoTest() {
//
//        Long usuarioId = 1L;
//        Usuario usuarioMock = new Usuario();
//        usuarioMock.setId(usuarioId);
//        usuarioMock.setNome("João");
//
//        VeiculoDTO dto = new VeiculoDTO();
//        dto.setMarca("Ford");
//        dto.setModelo("Ka");
//        dto.setUsuarioId(usuarioId);
//        dto.setKm(10000);
//        RegistroManutencao oleo = new RegistroManutencao();
//        oleo.setData(LocalDate.of(2025, 4, 10));
//        oleo.setKm(100);
//        dto.setUltTrocaOleo(oleo);
//        RegistroManutencao filtro = new RegistroManutencao();
//        filtro.setData(LocalDate.of(2025, 02, 10));
//        filtro.setKm(2000);
//        RegistroManutencao pastilhas = new RegistroManutencao();
//        pastilhas.setData(LocalDate.of(2025, 02, 10));
//        pastilhas.setKm(2000);
//
//        // Ensinamos os Mocks a se comportarem
//        // Quando buscar o dono pelo ID, retorna o usuarioMock que criamos
//        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioMock));
//
//        // Quando salvar qualquer veiculo, retorna um veiculo com ID gerado (simulando o banco)
//        when(veiculoRepository.save(any(Veiculo.class))).thenAnswer(invocation -> {
//            Veiculo v = invocation.getArgument(0);
//            v.setId(10L); // Simula o banco gerando ID
//            return v;
//        });
//
//
//        // --- AÇÃO (ACT) ---
//        // Chamamos o método real que queremos testar
//        Veiculo veiculoSalvo = veiculoService.cadastrarVeiculo(dto);
//
////        when(veiculoSalvo.pegarSaudeVeiculo(10L)).thenReturn(20);
//
//        // --- VERIFICAÇÃO (ASSERT) ---
//        // Verificamos se o resultado é o esperado
//        Assertions.assertNotNull(veiculoSalvo);
//        Assertions.assertEquals("Ford", veiculoSalvo.getMarca());
//        Assertions.assertEquals(usuarioMock, veiculoSalvo.getUsuario()); // Verifica se associou o dono
//        Assertions.assertEquals(0, veiculoSalvo.getSaudeVeiculo());
//
//        // Verifica se o repository.save foi chamado exatamente 1 vez
//        verify(veiculoRepository, times(1)).save(any(Veiculo.class));
//    }
}