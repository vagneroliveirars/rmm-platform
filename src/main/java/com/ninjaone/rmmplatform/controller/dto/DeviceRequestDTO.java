package com.ninjaone.rmmplatform.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class DeviceRequestDTO {

    @NotNull
    private UUID uuid;

    @NotBlank
    private String systemName;

    @NotNull
    private Long type;

    public DeviceRequestDTO() {
    }

    public DeviceRequestDTO(UUID uuid, String systemName, Long type) {
        this.uuid = uuid;
        this.systemName = systemName;
        this.type = type;
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

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
}
