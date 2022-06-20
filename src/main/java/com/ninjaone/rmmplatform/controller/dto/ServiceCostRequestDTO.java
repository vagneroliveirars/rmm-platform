package com.ninjaone.rmmplatform.controller.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class ServiceCostRequestDTO {

    @NotNull
    private Long serviceId;

    @NotNull
    private Long deviceTypeId;

    @NotNull
    @PositiveOrZero
    private BigDecimal amount;

    public ServiceCostRequestDTO() {
    }

    public ServiceCostRequestDTO(Long serviceId, Long deviceTypeId, BigDecimal amount) {
        this.serviceId = serviceId;
        this.deviceTypeId = deviceTypeId;
        this.amount = amount;
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
