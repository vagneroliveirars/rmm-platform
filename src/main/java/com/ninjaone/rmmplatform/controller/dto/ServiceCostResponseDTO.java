package com.ninjaone.rmmplatform.controller.dto;

import java.math.BigDecimal;

public class ServiceCostResponseDTO {

    private Long id;

    private Long serviceId;

    private Long deviceTypeId;

    private BigDecimal amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
