package com.FordCare.API.veiculo;

import com.FordCare.API.veiculo.veiculoDto.VeiculoDTO;
import com.FordCare.API.veiculo.veiculoDto.VeiculoResponseDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.List;


@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoService service;

    @PostMapping("/criar")
    public ResponseEntity<Veiculo> criarVeiculo(@RequestBody VeiculoDTO v, @NotNull UriComponentsBuilder uriBuilder) {
        Veiculo novoVeiculo = service.cadastrarVeiculo(v);

        URI uri = uriBuilder.path("/veiculo/{id}").buildAndExpand(novoVeiculo.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<@NotNull VeiculoResponseDTO> modificarVeiculo(@PathVariable Long id, @RequestBody VeiculoDTO novosDados) throws AccessDeniedException {
        VeiculoResponseDTO veiculoAlterado;
        veiculoAlterado = service.alterarInformacao(id, novosDados);
        return ResponseEntity.ok().body(veiculoAlterado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarVeiculo(@PathVariable Long id) throws AccessDeniedException {
        service.excluirVeiculo(id);
        return ResponseEntity.noContent().build();
    }

    //Traz todos os veículos do usuario
    @GetMapping("/listar")
    public ResponseEntity<List<VeiculoResponseDTO>> ListarVeiculoUsuario() {
        List<VeiculoResponseDTO> veiculosUsuario = service.listarPorUsuario();

        return ResponseEntity.ok(veiculosUsuario);
    }

    //Para que o SpringBoot saiba que estamos a pegar o ‘id’ da URL precisamos deixar o mesmo nome do id no parâmetro para que ele não confunda
    @GetMapping("/saude/{id}")
    public ResponseEntity<Integer> pegarSaude(@PathVariable Long id){
        Integer saude = service.pegarSaudeVeiculo(id);
        return ResponseEntity.ok(saude);
    }


    //traz recomendações conforme as informações do veiculo
    @GetMapping("/recomendacoes/{id}")
    public ResponseEntity<List<String>> buscarRecomendacoes(@PathVariable Long id){
        List<String> recomendacoes = service.recomendacoes(id);
        return ResponseEntity.ok().body(recomendacoes);
    }
    /*
     TODO: Fazer um endpoint para um administrador, onde ele pode ver todos os carros cadastrados e seus donos.
    */
}
