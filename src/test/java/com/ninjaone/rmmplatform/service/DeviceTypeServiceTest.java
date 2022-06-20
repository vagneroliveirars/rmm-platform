package com.ninjaone.rmmplatform.service;

import com.ninjaone.rmmplatform.exception.NotFoundException;
import com.ninjaone.rmmplatform.model.DeviceType;
import com.ninjaone.rmmplatform.repository.DeviceTypeRepository;
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
class DeviceTypeServiceTest {

    @Mock
    private DeviceTypeRepository deviceTypeRepository;

    @InjectMocks
    private DeviceTypeService deviceTypeService;

    private DeviceType deviceType;

    @BeforeEach
    void setup() {
        deviceType = new DeviceType(1L, "Windows Workstation");
    }

    @Test
    void save() {
        when(deviceTypeRepository.save(deviceType)).thenReturn(deviceType);
        assertEquals(deviceType, deviceTypeService.save(deviceType));
    }

    @Test
    void findAll() {
        List<DeviceType> types = List.of(deviceType);
        when(deviceTypeRepository.findAll()).thenReturn(types);
        assertEquals(types, deviceTypeService.findAll());
    }

    @Test
    void findById() {
        when(deviceTypeRepository.findById(deviceType.getId())).thenReturn(Optional.of(deviceType));
        assertEquals(deviceType, deviceTypeService.findById(deviceType.getId()));
    }

    @Test
    void notFoundById() {
        Long id = deviceType.getId();
        when(deviceTypeRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> deviceTypeService.findById(id));
    }

    @Test
    void deleteById() {
        deviceTypeService.deleteById(deviceType.getId());
        verify(deviceTypeRepository).deleteById(deviceType.getId());
    }

    @Test
    void deleteByIdNotFound() {
        Long id = deviceType.getId();
        doThrow(new EmptyResultDataAccessException(1)).when(deviceTypeRepository).deleteById(id);
        assertThrows(NotFoundException.class, () -> deviceTypeService.deleteById(id));
    }

}