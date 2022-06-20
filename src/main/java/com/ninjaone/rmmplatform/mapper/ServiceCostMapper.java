package com.ninjaone.rmmplatform.mapper;

import com.ninjaone.rmmplatform.controller.dto.ServiceCostResponseDTO;
import com.ninjaone.rmmplatform.model.ServiceCost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface ServiceCostMapper {

    @Mapping(source = "service.id", target = "serviceId")
    @Mapping(source = "deviceType.id", target = "deviceTypeId")
    ServiceCostResponseDTO toDTO(ServiceCost serviceCost);

}
