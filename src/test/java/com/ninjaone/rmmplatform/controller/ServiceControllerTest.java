package com.ninjaone.rmmplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.rmmplatform.Application;
import com.ninjaone.rmmplatform.controller.dto.ServiceRequestDTO;
import com.ninjaone.rmmplatform.exception.NotFoundException;
import com.ninjaone.rmmplatform.exception.ServiceAlreadyExistsException;
import com.ninjaone.rmmplatform.model.DeviceType;
import com.ninjaone.rmmplatform.model.Service;
import com.ninjaone.rmmplatform.model.ServiceCost;
import com.ninjaone.rmmplatform.model.ServiceType;
import com.ninjaone.rmmplatform.service.ServiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
class ServiceControllerTest {

    private static final String SERVICES_RESOURCE = "/services";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ServiceService serviceService;

    private Service service;

    @BeforeEach
    void setUp() {
        ServiceType serviceType = new ServiceType(3L, "Backup");
        service = new Service("Copy of data saved in the cloud", serviceType);
        service.setId(1L);
    }

    @Test
    void findAll() throws Exception {
        List<Service> services = List.of(service);
        when(serviceService.findAll()).thenReturn(services);

        mockMvc.perform(get(SERVICES_RESOURCE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(service.getId()))
                .andExpect(jsonPath("$[0].description").value(service.getDescription()))
                .andExpect(jsonPath("$[0].type.id").value(service.getType().getId()))
                .andExpect(jsonPath("$[0].type.description").value(service.getType().getDescription()));
    }

    @Test
    void findById() throws Exception {
        when(serviceService.findById(service.getId())).thenReturn(service);

        mockMvc.perform(get(SERVICES_RESOURCE + "/" + service.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(service.getId()))
                .andExpect(jsonPath("$.description").value(service.getDescription()))
                .andExpect(jsonPath("$.type.id").value(service.getType().getId()))
                .andExpect(jsonPath("$.type.description").value(service.getType().getDescription()));
    }

    @Test
    void findByIdNotFound() throws Exception {
        NotFoundException exception = new NotFoundException("Service not found");
        when(serviceService.findById(service.getId())).thenThrow(exception);

        String path = SERVICES_RESOURCE + "/" + service.getId();

        mockMvc.perform(get(path))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value(path))
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(exception.getMessage()));
    }

    @Test
    void create() throws Exception {
        ServiceRequestDTO request = new ServiceRequestDTO(service.getDescription(), service.getType().getId());

        when(serviceService.save(any())).thenReturn(service);

        mockMvc.perform(post(SERVICES_RESOURCE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(service.getId()))
                .andExpect(jsonPath("$.description").value(service.getDescription()))
                .andExpect(jsonPath("$.type.id").value(service.getType().getId()))
                .andExpect(jsonPath("$.type.description").value(service.getType().getDescription()));
    }

    @Test
    void createWithoutDescription() throws Exception {
        ServiceRequestDTO request = new ServiceRequestDTO(null, service.getType().getId());

        mockMvc.perform(post(SERVICES_RESOURCE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value(SERVICES_RESOURCE))
                .andExpect(jsonPath("$.status").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void createWithoutType() throws Exception {
        ServiceRequestDTO request = new ServiceRequestDTO(service.getDescription(), null);

        mockMvc.perform(post(SERVICES_RESOURCE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value(SERVICES_RESOURCE))
                .andExpect(jsonPath("$.status").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void serviceAlreadyExists() throws Exception {
        ServiceRequestDTO request = new ServiceRequestDTO(service.getDescription(), service.getType().getId());

        ServiceAlreadyExistsException exception = new ServiceAlreadyExistsException("Service already exists");
        when(serviceService.save(any())).thenThrow(exception);

        mockMvc.perform(post(SERVICES_RESOURCE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value(SERVICES_RESOURCE))
                .andExpect(jsonPath("$.status").value(CONFLICT.value()))
                .andExpect(jsonPath("$.error").value(CONFLICT.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(exception.getMessage()));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete(SERVICES_RESOURCE + "/" + service.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void getCostsById() throws Exception {
        DeviceType deviceType = new DeviceType(1L, "Windows Workstation");
        ServiceCost serviceCost = new ServiceCost(service, deviceType, BigDecimal.ONE);
        serviceCost.setId(2L);

        Set<ServiceCost> costs = Set.of(serviceCost);
        service.setCosts(costs);

        when(serviceService.findById(service.getId())).thenReturn(service);

        mockMvc.perform(get(SERVICES_RESOURCE + "/" + service.getId() + "/costs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(serviceCost.getId()))
                .andExpect(jsonPath("$[0].serviceId").value(serviceCost.getService().getId()))
                .andExpect(jsonPath("$[0].deviceTypeId").value(serviceCost.getDeviceType().getId()))
                .andExpect(jsonPath("$[0].amount").value(serviceCost.getAmount()));
    }
}