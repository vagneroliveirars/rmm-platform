package com.ninjaone.rmmplatform.mapper;

import com.ninjaone.rmmplatform.controller.dto.ServiceResponseDTO;
import com.ninjaone.rmmplatform.model.Service;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface ServiceMapper {

    ServiceResponseDTO toDTO(Service service);

}
