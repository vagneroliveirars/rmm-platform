package com.ninjaone.rmmplatform.controller.dto;

import javax.validation.constraints.NotBlank;

public class DeviceTypeRequestDTO {

    @NotBlank
    private String description;

    public DeviceTypeRequestDTO() {
    }

    public DeviceTypeRequestDTO(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
