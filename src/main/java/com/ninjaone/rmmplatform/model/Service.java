package com.ninjaone.rmmplatform.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @OneToOne(optional = false)
    private ServiceType type;

    @OneToMany(mappedBy = "service")
    private Set<ServiceCost> costs = new HashSet<>();

    public Service() {
    }

    public Service(String description, ServiceType type) {
        this.description = description;
        this.type = type;
    }

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

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public Set<ServiceCost> getCostsByDeviceType(DeviceType deviceType) {
        return costs.stream().filter(cost -> cost.getDeviceType().equals(deviceType)).collect(toSet());
    }

    public Set<ServiceCost> getCosts() {
        return costs;
    }

    public void setCosts(Set<ServiceCost> costs) {
        this.costs = costs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Service service = (Service) o;

        return new EqualsBuilder().append(id, service.id).append(description, service.description).append(type, service.type).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(description).append(type).toHashCode();
    }
}
