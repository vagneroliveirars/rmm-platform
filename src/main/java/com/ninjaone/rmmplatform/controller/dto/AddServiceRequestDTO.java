package com.ninjaone.rmmplatform.controller.dto;

import javax.validation.constraints.NotNull;

public class AddServiceRequestDTO {

    @NotNull
    private Long serviceId;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}
