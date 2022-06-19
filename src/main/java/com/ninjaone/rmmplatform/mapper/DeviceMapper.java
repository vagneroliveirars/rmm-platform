package com.ninjaone.rmmplatform.mapper;

import com.ninjaone.rmmplatform.controller.dto.DeviceResponseDTO;
import com.ninjaone.rmmplatform.model.Device;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface DeviceMapper {

    DeviceResponseDTO toDTO(Device device);

}
