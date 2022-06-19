package com.ninjaone.rmmplatform.controller.dto;

import java.math.BigDecimal;

public class ServiceCostResponseDTO {

    private Long id;

    private ServiceResponseDTO service;

    private DeviceTypeResponseDTO deviceType;

    private BigDecimal amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceResponseDTO getService() {
        return service;
    }

    public void setService(ServiceResponseDTO service) {
        this.service = service;
    }

    public DeviceTypeResponseDTO getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceTypeResponseDTO deviceType) {
        this.deviceType = deviceType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
