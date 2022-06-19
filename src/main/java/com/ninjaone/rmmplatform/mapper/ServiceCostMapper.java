package com.ninjaone.rmmplatform.mapper;

import com.ninjaone.rmmplatform.controller.dto.ServiceCostResponseDTO;
import com.ninjaone.rmmplatform.model.ServiceCost;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface ServiceCostMapper {

    ServiceCostResponseDTO toDTO(ServiceCost serviceCost);

}
