package com.ninjaone.rmmplatform.controller.dto;

import java.math.BigDecimal;
import java.util.List;

public class DevicesResponseDTO {

    public DevicesResponseDTO(List<DeviceResponseDTO> devices) {
        this.devices = devices;
    }

    private List<DeviceResponseDTO> devices;

    public List<DeviceResponseDTO> getDevices() {
        return devices;
    }

    public BigDecimal getTotalMonthlyCost() {
        return devices.stream().map(DeviceResponseDTO::getTotalMonthlyCost).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
