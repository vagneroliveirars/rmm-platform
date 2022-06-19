package com.ninjaone.rmmplatform.mapper;

import com.ninjaone.rmmplatform.controller.dto.ServiceTypeRequestDTO;
import com.ninjaone.rmmplatform.controller.dto.ServiceTypeResponseDTO;
import com.ninjaone.rmmplatform.model.ServiceType;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface ServiceTypeMapper {

    ServiceTypeResponseDTO toDTO(ServiceType serviceType);

    ServiceType toEntity(ServiceTypeRequestDTO serviceTypeRequestDTO);

}
