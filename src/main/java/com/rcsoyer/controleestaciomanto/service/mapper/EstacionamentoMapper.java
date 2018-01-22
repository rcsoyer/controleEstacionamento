package com.rcsoyer.controleestaciomanto.service.mapper;

import com.rcsoyer.controleestaciomanto.domain.*;
import com.rcsoyer.controleestaciomanto.service.dto.EstacionamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Estacionamento and its DTO EstacionamentoDTO.
 */
@Mapper(componentModel = "spring", uses = {VeiculoMapper.class, PatioMapper.class})
public interface EstacionamentoMapper extends EntityMapper<EstacionamentoDTO, Estacionamento> {

    @Mapping(source = "veiculo.id", target = "veiculoId")
    @Mapping(source = "patio.id", target = "patioId")
    EstacionamentoDTO toDto(Estacionamento estacionamento);

    @Mapping(source = "veiculoId", target = "veiculo")
    @Mapping(source = "patioId", target = "patio")
    Estacionamento toEntity(EstacionamentoDTO estacionamentoDTO);

    default Estacionamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Estacionamento estacionamento = new Estacionamento();
        estacionamento.setId(id);
        return estacionamento;
    }
}
