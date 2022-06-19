package com.ninjaone.rmmplatform.controller.dto;

import javax.validation.constraints.NotBlank;

public class ServiceTypeRequestDTO {

    @NotBlank
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
