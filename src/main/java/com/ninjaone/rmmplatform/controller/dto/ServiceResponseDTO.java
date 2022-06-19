package com.ninjaone.rmmplatform.controller.dto;

public class ServiceResponseDTO {

    private Long id;

    private String description;

    private ServiceTypeResponseDTO type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServiceTypeResponseDTO getType() {
        return type;
    }

    public void setType(ServiceTypeResponseDTO type) {
        this.type = type;
    }

}
