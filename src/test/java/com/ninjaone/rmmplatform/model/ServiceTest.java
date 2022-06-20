package com.ninjaone.rmmplatform.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceTest {

    @Test
    void getCostsByDeviceType() {
        DeviceType windows = new DeviceType(1L, "Windows Workstation");
        DeviceType mac = new DeviceType(2L, "Mac");

        ServiceType serviceType = new ServiceType(1L, "Antivirus");
        Service service = new Service("Program designed to detect and remove viruses and other kinds of malicious software", serviceType);

        ServiceCost costForWindows = new ServiceCost(service, windows, BigDecimal.ONE);
        ServiceCost costForMac = new ServiceCost(service, mac, BigDecimal.TEN);

        service.setCosts(Set.of(costForWindows, costForMac));

        assertEquals(Set.of(costForWindows), service.getCostsByDeviceType(windows));
        assertEquals(Set.of(costForMac), service.getCostsByDeviceType(mac));
    }

}