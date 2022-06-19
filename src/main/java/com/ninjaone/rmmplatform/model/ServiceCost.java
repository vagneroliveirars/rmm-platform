package com.ninjaone.rmmplatform.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"service_id", "device_type_id"}))
@Entity
public class ServiceCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne(optional = false)
    @JoinColumn(name = "device_type_id")
    private DeviceType deviceType;

    @Column(nullable = false)
    private BigDecimal amount;

    public ServiceCost() {
    }

    public ServiceCost(Service service, DeviceType deviceType, BigDecimal amount) {
        this.service = service;
        this.deviceType = deviceType;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ServiceCost that = (ServiceCost) o;

        return new EqualsBuilder().append(id, that.id).append(service, that.service).append(deviceType, that.deviceType).append(amount, that.amount).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(service).append(deviceType).append(amount).toHashCode();
    }
}
