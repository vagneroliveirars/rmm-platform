package com.ninjaone.rmmplatform.service;

import com.ninjaone.rmmplatform.controller.dto.DeviceRequestDTO;
import com.ninjaone.rmmplatform.exception.DeviceAlreadyExistsException;
import com.ninjaone.rmmplatform.exception.NotFoundException;
import com.ninjaone.rmmplatform.model.Device;
import com.ninjaone.rmmplatform.model.DeviceType;
import com.ninjaone.rmmplatform.model.Service;
import com.ninjaone.rmmplatform.model.ServiceType;
import com.ninjaone.rmmplatform.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private DeviceTypeService deviceTypeService;

    @Mock
    private ServiceService serviceService;

    @InjectMocks
    private DeviceService deviceService;

    private Device device;

    private DeviceRequestDTO request;

    @BeforeEach
    void setUp() {
        device = new Device(UUID.randomUUID(), "Windows 10", new DeviceType(1L, "Windows Workstation"));
        request = new DeviceRequestDTO(device.getUuid(), device.getSystemName(), device.getType().getId());
    }

    @Test
    void save() {
        when(deviceTypeService.findById(request.getType())).thenReturn(device.getType());
        when(deviceRepository.save(device)).thenReturn(device);
        assertEquals(device, deviceService.save(request));
    }

    @Test
    void deviceAlreadyExists() {
        when(deviceTypeService.findById(request.getType())).thenReturn(device.getType());
        when(deviceRepository.save(device)).thenThrow(new DataIntegrityViolationException("uuid already exists"));
        assertThrows(DeviceAlreadyExistsException.class, () -> deviceService.save(request));
    }

    @Test
    void findAll() {
        List<Device> devices = List.of(device);
        when(deviceRepository.findAll()).thenReturn(devices);
        assertEquals(devices, deviceService.findAll());
    }

    @Test
    void findById() {
        when(deviceRepository.findById(device.getId())).thenReturn(Optional.of(device));
        assertEquals(device, deviceService.findById(device.getId()));
    }

    @Test
    void findByIdNotFound() {
        Long id = device.getId();
        when(deviceRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> deviceService.findById(id));
    }

    @Test
    void updateById() {
        when(deviceRepository.findById(device.getId())).thenReturn(Optional.of(device));
        when(deviceRepository.save(device)).thenReturn(device);
        deviceService.updateById(device.getId(), request);
        verify(deviceRepository).findById(device.getId());
        verify(deviceRepository).save(device);
    }

    @Test
    void updateByIdWithAlreadyExistingUUID() {
        Long id = device.getId();
        when(deviceRepository.findById(id)).thenReturn(Optional.of(device));
        when(deviceRepository.save(device)).thenThrow(new DataIntegrityViolationException(String.format("Device with UUID %s already exists", request.getUuid())));
        assertThrows(DeviceAlreadyExistsException.class, () -> deviceService.updateById(id, request));
    }

    @Test
    void deleteById() {
        deviceService.deleteById(device.getId());
        verify(deviceRepository).deleteById(device.getId());
    }

    @Test
    void deleteByIdNotFound() {
        Long id = device.getId();
        doThrow(new EmptyResultDataAccessException(1)).when(deviceRepository).deleteById(id);
        assertThrows(NotFoundException.class, () -> deviceService.deleteById(id));
    }

    @Test
    void getServices() {
        when(deviceRepository.findById(device.getId())).thenReturn(Optional.of(device));
        assertEquals(device.getServices(), deviceService.getServices(device.getId()));
    }

    @Test
    void addService() {
        Long serviceId = 3L;
        ServiceType serviceType = new ServiceType(3L, "Backup");
        Service service = new Service("Copy of data saved in the cloud", serviceType);

        when(deviceRepository.findById(device.getId())).thenReturn(Optional.of(device));
        when(serviceService.findById(serviceId)).thenReturn(service);
        when(deviceRepository.save(device)).thenReturn(device);

        assertEquals(device.getServices(), deviceService.addService(device.getId(), serviceId));
        verify(deviceRepository).save(device);
    }

    @Test
    void removeService() {
        ServiceType serviceType = new ServiceType(3L, "Backup");
        Service service = new Service("Copy of data saved in the cloud", serviceType);
        service.setId(3L);
        device.getServices().add(service);

        when(deviceRepository.findById(device.getId())).thenReturn(Optional.of(device));
        when(deviceRepository.save(device)).thenReturn(device);
        deviceService.removeService(device.getId(), service.getId());
        verify(deviceRepository).save(device);
    }

}