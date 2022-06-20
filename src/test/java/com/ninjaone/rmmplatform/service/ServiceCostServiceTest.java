package com.ninjaone.rmmplatform.service;

import com.ninjaone.rmmplatform.controller.dto.ServiceCostRequestDTO;
import com.ninjaone.rmmplatform.exception.NotFoundException;
import com.ninjaone.rmmplatform.exception.ServiceCostAlreadyExistsException;
import com.ninjaone.rmmplatform.model.DeviceType;
import com.ninjaone.rmmplatform.model.Service;
import com.ninjaone.rmmplatform.model.ServiceCost;
import com.ninjaone.rmmplatform.model.ServiceType;
import com.ninjaone.rmmplatform.repository.ServiceCostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceCostServiceTest {

    @Mock
    private ServiceCostRepository serviceCostRepository;

    @Mock
    private ServiceService serviceService;

    @Mock
    private DeviceTypeService deviceTypeService;

    @InjectMocks
    private ServiceCostService serviceCostService;

    private Service service;

    private DeviceType deviceType;

    private ServiceCost serviceCost;

    private ServiceCostRequestDTO request;

    @BeforeEach
    void setup() {
        ServiceType serviceType = new ServiceType(3L, "Backup");
        service = new Service("Copy of data saved in the cloud", serviceType);
        service.setId(3L);

        deviceType = new DeviceType(1L, "Windows Workstation");

        serviceCost = new ServiceCost(service, deviceType, BigDecimal.TEN);

        request = new ServiceCostRequestDTO(serviceCost.getService().getId(), serviceCost.getDeviceType().getId(), serviceCost.getAmount());
    }

    @Test
    void save() {
        when(serviceService.findById(request.getServiceId())).thenReturn(service);
        when(deviceTypeService.findById(request.getDeviceTypeId())).thenReturn(deviceType);
        when(serviceCostRepository.save(serviceCost)).thenReturn(serviceCost);
        assertEquals(serviceCost, serviceCostService.save(request));
    }

    @Test
    void saveAlreadyExisting() {
        when(serviceService.findById(request.getServiceId())).thenReturn(service);
        when(deviceTypeService.findById(request.getDeviceTypeId())).thenReturn(deviceType);
        when(serviceCostRepository.save(serviceCost)).thenThrow(new DataIntegrityViolationException("Service cost already exists"));
        assertThrows(ServiceCostAlreadyExistsException.class, () -> serviceCostService.save(request));
    }

    @Test
    void findAll() {
        List<ServiceCost> costs = List.of(serviceCost);
        when(serviceCostRepository.findAll()).thenReturn(costs);
        assertEquals(costs, serviceCostService.findAll());
    }

    @Test
    void findById() {
        when(serviceCostRepository.findById(serviceCost.getId())).thenReturn(Optional.of(serviceCost));
        assertEquals(serviceCost, serviceCostService.findById(serviceCost.getId()));
    }

    @Test
    void findByIdNotFound() {
        Long id = serviceCost.getId();
        when(serviceCostRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> serviceCostService.findById(id));
    }

    @Test
    void update() {
        when(serviceCostRepository.findById(serviceCost.getId())).thenReturn(Optional.of(serviceCost));
        when(serviceCostRepository.save(serviceCost)).thenReturn(serviceCost);
        serviceCostService.update(serviceCost.getId(), BigDecimal.ONE);
        verify(serviceCostRepository).save(serviceCost);
    }

    @Test
    void deleteById() {
        serviceCostService.deleteById(serviceCost.getId());
        verify(serviceCostRepository).deleteById(serviceCost.getId());
    }

    @Test
    void deleteByIdNotFound() {
        Long id = serviceCost.getId();
        doThrow(new EmptyResultDataAccessException(1)).when(serviceCostRepository).deleteById(id);
        assertThrows(NotFoundException.class, () -> serviceCostService.deleteById(id));
    }

}