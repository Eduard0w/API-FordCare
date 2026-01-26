package com.FordCare.API.mapper;

import com.FordCare.API.veiculo.Veiculo;
import com.FordCare.API.veiculo.veiculoDto.VeiculoDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/* Foi utilizado a biblioteca MapStruct, ela automatiza a criação, nesse exemplo,
de if/else que seriam necessários para troca das informações */
// componentModel = "spring" permite usar @Autowired no Service
@Mapper(componentModel = "spring")
public interface VeiculoMapper {

    // Essa anotação diz: "Se vier null no DTO, IGNORE e mantenha o que está no banco"
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void atualizarVeiculo(VeiculoDTO dto, @MappingTarget Veiculo entity);
}