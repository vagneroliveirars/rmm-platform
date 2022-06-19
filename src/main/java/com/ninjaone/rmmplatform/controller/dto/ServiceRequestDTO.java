package com.ninjaone.rmmplatform.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ServiceRequestDTO {

    @NotBlank
    private String description;

    @NotNull
    private Long type;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
}
