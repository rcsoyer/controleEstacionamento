package com.rcsoyer.controleestaciomanto.service.mapper;

import com.rcsoyer.controleestaciomanto.domain.*;
import com.rcsoyer.controleestaciomanto.service.dto.VeiculoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Veiculo and its DTO VeiculoDTO.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface VeiculoMapper extends EntityMapper<VeiculoDTO, Veiculo> {

    @Mapping(source = "cliente.id", target = "clienteId")
    VeiculoDTO toDto(Veiculo veiculo);

    @Mapping(source = "clienteId", target = "cliente")
    Veiculo toEntity(VeiculoDTO veiculoDTO);

    default Veiculo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Veiculo veiculo = new Veiculo();
        veiculo.setId(id);
        return veiculo;
    }
}
