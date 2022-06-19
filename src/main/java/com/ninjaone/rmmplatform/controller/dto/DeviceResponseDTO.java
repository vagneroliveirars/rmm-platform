package com.ninjaone.rmmplatform.controller.dto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public class DeviceResponseDTO {

    private Long id;

    private UUID uuid;

    private String systemName;

    private DeviceTypeResponseDTO type;

    private Set<ServiceResponseDTO> services;

    private BigDecimal totalMonthlyCost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public DeviceTypeResponseDTO getType() {
        return type;
    }

    public void setType(DeviceTypeResponseDTO type) {
        this.type = type;
    }

    public Set<ServiceResponseDTO> getServices() {
        return services;
    }

    public void setServices(Set<ServiceResponseDTO> services) {
        this.services = services;
    }

    public BigDecimal getTotalMonthlyCost() {
        return totalMonthlyCost;
    }

    public void setTotalMonthlyCost(BigDecimal totalMonthlyCost) {
        this.totalMonthlyCost = totalMonthlyCost;
    }
}
