package com.ninjaone.rmmplatform.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid;

    @Column(nullable = false)
    private String systemName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_id")
    private DeviceType type;

    @ManyToMany
    @JoinTable(
            name = "device_service",
            joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<Service> services = new HashSet<>();

    public Device() {}

    public Device(UUID uuid, String systemName, DeviceType type) {
        this.uuid = uuid;
        this.systemName = systemName;
        this.type = type;
    }

    public BigDecimal getTotalMonthlyCost() {
        return services.stream()
                .flatMap(service -> service.getCostsByDeviceType(type).stream())
                .map(ServiceCost::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

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

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

        return new EqualsBuilder().append(id, device.id).append(uuid, device.uuid).append(systemName, device.systemName).append(type, device.type).append(services, device.services).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(uuid).append(systemName).append(type).append(services).toHashCode();
    }
}
