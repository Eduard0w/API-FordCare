package com.FordCare.API.veiculo;

import com.FordCare.API.mapper.VeiculoMapper;
import com.FordCare.API.model.RegistroManutencao;
import com.FordCare.API.usuario.Usuario;
import com.FordCare.API.usuario.UsuarioRepository;
import com.FordCare.API.veiculo.veiculoDto.VeiculoDTO;
import com.FordCare.API.veiculo.veiculoDto.VeiculoResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VeiculoMapper mapper;

    @Autowired
    private VeiculoSeguranca seguranca;

    public Veiculo cadastrarVeiculo(@NotNull VeiculoDTO dados) {
        //Busca o dono do carro através do Token
        Usuario dono = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //Cria o Veiculo e associa os dados
        Veiculo veiculo = new Veiculo();
        veiculo.setMarca(dados.getMarca());
        veiculo.setModelo(dados.getModelo());
        veiculo.setAnoVeiculo(dados.getAnoVeiculo());
        veiculo.setPlaca(dados.getPlaca());
        veiculo.setTipoCombustivel(dados.getTipoCombustivel());
        veiculo.setKm(dados.getKm());
        veiculo.setUltTrocaOleo(dados.getUltTrocaOleo());
        veiculo.setUltTrocaFiltro(dados.getUltTrocaFiltro());
        veiculo.setUltTrocaPastilhas(dados.getUltTrocaPastilhas());
        veiculo.setAlertaPainel(dados.getAlertaPainel());
        veiculo.setTipoUso(dados.getTipoUso());
        veiculo.setKmMedio(dados.getKmMedio());
        veiculo.setImagemVeiculo(dados.getImagemVeiculo());

        //Associa o veiculo ao dono
        veiculo.setUsuario(dono);

        calcularSaude(veiculo);

        return veiculoRepository.save(veiculo);
    }

    public VeiculoResponseDTO alterarInformacao(@NotNull Long veiculoId, @NotNull VeiculoDTO novosDados) throws AccessDeniedException {
        Veiculo veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado!"));

//        Ao invés disso:
//        veiculo.setMarca(novosDados.getMarca());
//        veiculo.setModelo(novosDados.getModelo());
//        veiculo.setAnoVeiculo(novosDados.getAnoVeiculo());
//        veiculo.setPlaca(novosDados.getPlaca());
//        veiculo.setTipoCombustivel(novosDados.getTipoCombustivel());
//        veiculo.setKm(novosDados.getKm());
//        veiculo.setUltTrocaOleo(novosDados.getUltTrocaOleo());
//        veiculo.setUltTrocaFiltro(novosDados.getUltTrocaFiltro());
//        veiculo.setUltTrocaPastilhas(novosDados.getUltTrocaPastilhas());
//        veiculo.setAlertaPainel(novosDados.getAlertaPainel());
//        veiculo.setTipoUso(novosDados.getTipoUso());
//        veiculo.setKmMedio(novosDados.getKmMedio());
//        veiculo.setSaudeVeiculo(novosDados.getSaudeVeiculo());
//        veiculo.setImagemVeiculo(novosDados.getImageVeiculo());

//        Usamos isso:

        if(!seguranca.validarUsuario(veiculo)){
            throw new AccessDeniedException("Você não tem permição para procurar esse veiculo");
        }
        mapper.atualizarVeiculo(novosDados, veiculo);

        calcularSaude(veiculo);
        veiculoRepository.save(veiculo);

        return mapper.veiculoParaVeiculoResponseDTO(veiculo);
    }

    public void excluirVeiculo(Long veiculoId) throws AccessDeniedException {
        Optional<Veiculo> veiculoDeletar = veiculoRepository.findById(veiculoId);
        if(!seguranca.validarUsuario(veiculoDeletar.get())){
            throw new AccessDeniedException("Você não tem permição para deletar esse veiculo");
        }
        veiculoRepository.deleteById(veiculoId);
    }

    // Mé7odo para listar veículos de um usuário específico
    public List<VeiculoResponseDTO> listarPorUsuario() {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return veiculoRepository.findByUsuarioId(usuarioLogado.getId());
    }

    public void calcularSaude(@NotNull Veiculo v){
        //TODO: Calculo de saúde do veiculo.
        Integer saude = 100;
        LocalDate dataAtual = LocalDate.now();

        int perdaOleo = calcularPerdaManutencao(v.getUltTrocaOleo(), v.getKm(), dataAtual);
        int perdaFiltro = calcularPerdaManutencao(v.getUltTrocaFiltro(), v.getKm(), dataAtual);
        int perdaPastilhas = calcularPerdaManutencao(v.getUltTrocaPastilhas(), v.getKm(), dataAtual);

        int perdaAlertas = calcularPerdaPorAlertas(v.getAlertaPainel());

        int totalPerda = perdaOleo + perdaFiltro + perdaPastilhas + perdaAlertas;
        saude -= totalPerda;

        if (saude < 0) saude = 0;

        v.setSaudeVeiculo(saude);
//        veiculoRepository.save(v);
    }

    public Integer pegarSaudeVeiculo(Long id) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));

        return veiculo.getSaudeVeiculo() != null ? veiculo.getSaudeVeiculo() : 0;
    }


    private int calcularPerdaManutencao(RegistroManutencao registro, Integer kmAtual, LocalDate dataAtual){
        if(registro == null) return 50; //Se nunca trocou, penalidade maxima...

        long dias = ChronoUnit.DAYS.between(registro.getData(), dataAtual);
        int perdaData = 0;
        if (dias > 365) perdaData = 30;
        else if (dias > 180) perdaData = 15;

        // Penalidade por KM
        int perdaKm = 0;
        if (kmAtual != null && registro.getKm() != null) {
            int kmRodados = kmAtual - registro.getKm();
            if (kmRodados > 15000) perdaKm = 40;
            else if (kmRodados > 10000) perdaKm = 20;
        }

        // Retorna o pior cenário (O que for mais grave)
        return Math.max(perdaData, perdaKm);
    }

    private int calcularPerdaPorAlertas(@NotNull List<String> alertas) {
        if(alertas.isEmpty()) return 0;

        int perdaTotal = 0;

        for(String alerta : alertas) {
            String a = alerta.toLowerCase();
            if (a.contains("bateria") || a.contains("luz do motor") || a.contains("temperatura")) {
                perdaTotal += 50; // Falhas CRÍTICAS
            } else if (a.contains("abs") || a.contains("pressão de óleo")) {
                perdaTotal += 20; // Falhas MÉDIAS
            }
        }

        return Math.min(perdaTotal, 100);
    }

    public List<String> recomendacoes(Long id){
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));

        List<String> listaRecomendacoes = new ArrayList<>();

        List<String> alertas = veiculo.getAlertaPainel();

        if (alertas != null) {
            for(String alerta : alertas){
                String a = alerta.toLowerCase();

                if (a.contains("bateria")) {
                    listaRecomendacoes.add("Sistema Elétrico: Verifique a vida útil da bateria e o alternador.");
                }
                else if (a.contains("motor")) {
                    listaRecomendacoes.add("Motor: Risco de falha grave. Procure um mecânico urgente para scanner.");
                }
                else if (a.contains("temperatura")) {
                    listaRecomendacoes.add("Superaquecimento: Pare o veículo! Verifique água e radiador.");
                }
                else if (a.contains("abs")) {
                    listaRecomendacoes.add("Segurança: Falha nos freios detectada. Dirija com cautela até a oficina.");
                }
                else if (a.contains("óleo")) {
                    listaRecomendacoes.add("Lubrificação: Nível ou pressão do óleo baixos. Risco de fundir o motor.");
                }
            }
        }

        if (veiculo.getUltTrocaOleo() != null) {
            int kmRodados = veiculo.getKm() - veiculo.getUltTrocaOleo().getKm();
            if (kmRodados > 10000) {
                listaRecomendacoes.add("Manutenção: Já passou da hora de trocar o óleo (Rodou " + kmRodados + "km).");
            }
        }

        if (veiculo.getKm() > 50000 && veiculo.getKm() % 10000 == 0) {
            listaRecomendacoes.add("Rotina: Seu carro está com quilometragem alta. Verifique correia dentada e pneus.");
        }

        if (listaRecomendacoes.isEmpty()) {
            listaRecomendacoes.add("Tudo certo! Seu veículo está em ótimas condições.");
        }

        return listaRecomendacoes;
    }
}
