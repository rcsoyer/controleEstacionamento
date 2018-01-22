package com.rcsoyer.controleestaciomanto.service.mapper;

import com.rcsoyer.controleestaciomanto.domain.*;
import com.rcsoyer.controleestaciomanto.service.dto.PatioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Patio and its DTO PatioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PatioMapper extends EntityMapper<PatioDTO, Patio> {



    default Patio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Patio patio = new Patio();
        patio.setId(id);
        return patio;
    }
}
