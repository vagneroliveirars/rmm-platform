package com.ninjaone.rmmplatform.controller.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DevicesResponseDTOTest {

    @Test
    void getTotalMonthlyCost() {
        DeviceResponseDTO windows = new DeviceResponseDTO();
        windows.setTotalMonthlyCost(BigDecimal.TEN);

        DeviceResponseDTO linux = new DeviceResponseDTO();
        linux.setTotalMonthlyCost(BigDecimal.ONE);

        DeviceResponseDTO mac = new DeviceResponseDTO();
        mac.setTotalMonthlyCost(new BigDecimal("7.99"));

        List<DeviceResponseDTO> devices = List.of(windows, linux, mac);
        DevicesResponseDTO devicesResponse = new DevicesResponseDTO(devices);
        BigDecimal totalMonthlyCost = devices.stream().map(DeviceResponseDTO::getTotalMonthlyCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(totalMonthlyCost, devicesResponse.getTotalMonthlyCost());
    }

}