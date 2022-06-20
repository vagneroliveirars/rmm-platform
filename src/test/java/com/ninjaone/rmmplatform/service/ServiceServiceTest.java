package com.ninjaone.rmmplatform.service;

import com.ninjaone.rmmplatform.controller.dto.ServiceRequestDTO;
import com.ninjaone.rmmplatform.exception.NotFoundException;
import com.ninjaone.rmmplatform.model.Service;
import com.ninjaone.rmmplatform.model.ServiceType;
import com.ninjaone.rmmplatform.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceServiceTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private ServiceTypeService serviceTypeService;

    @InjectMocks
    private ServiceService serviceService;

    private ServiceType serviceType;

    private Service service;

    private ServiceRequestDTO request;

    @BeforeEach
    void setUp() {
        serviceType = new ServiceType(3L, "Backup");
        service = new Service("Copy of data saved in the cloud", serviceType);
        request = new ServiceRequestDTO(service.getDescription(), service.getType().getId());
    }

    @Test
    void save() {
        when(serviceTypeService.findById(request.getType())).thenReturn(serviceType);
        when(serviceRepository.save(service)).thenReturn(service);
        assertEquals(service, serviceService.save(request));
    }

    @Test
    void findAll() {
        List<Service> services = List.of(service);
        when(serviceRepository.findAll()).thenReturn(services);
        assertEquals(services, serviceService.findAll());
    }

    @Test
    void findById() {
        when(serviceRepository.findById(service.getId())).thenReturn(Optional.of(service));
        assertEquals(service, serviceService.findById(service.getId()));
    }

    @Test
    void findByIdNotFound() {
        Long id = service.getId();
        when(serviceRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> serviceService.findById(id));
    }

    @Test
    void deleteById() {
        serviceService.deleteById(service.getId());
        verify(serviceRepository).deleteById(service.getId());
    }

    @Test
    void deleteByIdNotFound() {
        Long id = service.getId();
        doThrow(new EmptyResultDataAccessException(1)).when(serviceRepository).deleteById(id);
        assertThrows(NotFoundException.class, () -> serviceService.deleteById(id));
    }
}