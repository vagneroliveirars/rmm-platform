package com.ninjaone.rmmplatform.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeviceTest {

    @Test
    void getTotalMonthlyCost() {
        DeviceType deviceType = new DeviceType(1L, "Windows Workstation");

        ServiceType antiVirusServiceType = new ServiceType(1L, "Antivirus");
        Service antiVirusService = new Service("Program designed to detect and remove viruses and other kinds of malicious software", antiVirusServiceType);

        ServiceType backupServiceType = new ServiceType(1L, "Backup");
        Service backupService = new Service("Copy of data saved in the cloud", backupServiceType);

        ServiceCost antiVirusServiceCost = new ServiceCost(antiVirusService, deviceType, BigDecimal.ONE);
        ServiceCost backupServiceCost = new ServiceCost(backupService, deviceType, BigDecimal.TEN);

        antiVirusService.setCosts(Set.of(antiVirusServiceCost));
        backupService.setCosts(Set.of(backupServiceCost));

        Device device = new Device(UUID.randomUUID(), "Windows 10", deviceType);
        device.setServices(Set.of(antiVirusService, backupService));

        assertEquals(antiVirusServiceCost.getAmount().add(backupServiceCost.getAmount()), device.getTotalMonthlyCost());;
    }

}