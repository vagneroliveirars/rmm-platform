package com.ninjaone.rmmplatform.mapper;

import com.ninjaone.rmmplatform.controller.dto.DeviceTypeRequestDTO;
import com.ninjaone.rmmplatform.controller.dto.DeviceTypeResponseDTO;
import com.ninjaone.rmmplatform.model.DeviceType;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface DeviceTypeMapper {

    DeviceTypeResponseDTO toDTO(DeviceType deviceType);

    DeviceType toEntity(DeviceTypeRequestDTO deviceTypeRequestDTO);

}
