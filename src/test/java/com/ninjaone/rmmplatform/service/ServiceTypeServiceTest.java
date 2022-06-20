package com.ninjaone.rmmplatform.service;

import com.ninjaone.rmmplatform.exception.NotFoundException;
import com.ninjaone.rmmplatform.model.ServiceType;
import com.ninjaone.rmmplatform.repository.ServiceTypeRepository;
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
class ServiceTypeServiceTest {

    @Mock
    private ServiceTypeRepository serviceTypeRepository;

    @InjectMocks
    private ServiceTypeService serviceTypeService;

    private ServiceType serviceType;

    @BeforeEach
    void setUp() {
        serviceType = new ServiceType(1L, "Device");
    }

    @Test
    void save() {
        when(serviceTypeRepository.save(serviceType)).thenReturn(serviceType);
        assertEquals(serviceType, serviceTypeService.save(serviceType));
    }

    @Test
    void findAll() {
        List<ServiceType> types = List.of(serviceType);
        when(serviceTypeRepository.findAll()).thenReturn(types);
        assertEquals(types, serviceTypeService.findAll());
    }

    @Test
    void findById() {
        when(serviceTypeRepository.findById(serviceType.getId())).thenReturn(Optional.of(serviceType));
        assertEquals(serviceType, serviceTypeService.findById(serviceType.getId()));
    }

    @Test
    void findByIdNotFound() {
        Long id = serviceType.getId();
        when(serviceTypeRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> serviceTypeService.findById(id));
    }

    @Test
    void deleteById() {
        serviceTypeService.deleteById(serviceType.getId());
        verify(serviceTypeRepository).deleteById(serviceType.getId());
    }

    @Test
    void deleteByIdNotFound() {
        Long id = serviceType.getId();
        doThrow(new EmptyResultDataAccessException(1)).when(serviceTypeRepository).deleteById(id);
        assertThrows(NotFoundException.class, () -> serviceTypeService.deleteById(id));
    }

}