package com.FordCare.API.veiculo;

import com.FordCare.API.veiculo.veiculoDto.VeiculoDTO;
import lombok.Data;
import org.apache.catalina.Service;
import org.apache.coyote.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

// API de exemplo: https://github.com/RameshMF/springboot-blog-rest-api/blob/main/src/main/java/com/springboot/blog/config/SecurityConfig.java

@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoService service;


    // https://medium.com/@michel.marciano1984/spring-boot-na-pr%C3%A1tica-parte-2-eac9bdcab17a
    @PostMapping("/criar")
    public ResponseEntity<Veiculo> criarVeiculo(@RequestBody VeiculoDTO v, @NotNull UriComponentsBuilder uriBuilder) {
        Veiculo novoVeiculo = service.cadastrarVeiculo(v);

        URI uri = uriBuilder.path("/veiculo/{id}").buildAndExpand(novoVeiculo.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Veiculo> modificarVeiculo(@PathVariable Long id, @RequestBody VeiculoDTO novosDados){
        Veiculo veiculoAlterado = service.alterarInformacao(id, novosDados);
        return ResponseEntity.ok().body(veiculoAlterado);
    }

//    @DeleteMapping("/deletar/{id}")
//    public void deletarVeiculo(@PathVariable Long id){
//        service.excluirVeiculo(id);
//    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity deletarVeiculo(@PathVariable Long id){
        service.excluirVeiculo(id);
        return ResponseEntity.noContent().build();
    }

    //Traz todos os veículos do usuario
    //Para que o SpringBoot saiba que estamos a pegar o ‘id’ da URL precisamos deixar o mesmo nome do id no parâmetro para que ele não confunda
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Veiculo>> printVeiculo(@PathVariable Long id) {
        List<Veiculo> veiculosUsuario = service.listarPorUsuario(id);

        return ResponseEntity.ok(veiculosUsuario);
    }

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
