package com.ninjaone.rmmplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.rmmplatform.Application;
import com.ninjaone.rmmplatform.controller.dto.ServiceCostRequestDTO;
import com.ninjaone.rmmplatform.controller.dto.ServiceCostUpdateRequestDTO;
import com.ninjaone.rmmplatform.exception.NotFoundException;
import com.ninjaone.rmmplatform.exception.ServiceCostAlreadyExistsException;
import com.ninjaone.rmmplatform.model.DeviceType;
import com.ninjaone.rmmplatform.model.Service;
import com.ninjaone.rmmplatform.model.ServiceCost;
import com.ninjaone.rmmplatform.model.ServiceType;
import com.ninjaone.rmmplatform.service.ServiceCostService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
class ServiceCostControllerTest {

    private static final String SERVICE_COSTS_RESOURCE = "/service-costs";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ServiceCostService serviceCostService;

    private ServiceCost serviceCost;

    private ServiceCostRequestDTO serviceCostRequest;

    @BeforeEach
    void setUp() {
        ServiceType serviceType = new ServiceType(1L, "Device");
        Service service = new Service("Desktops and notebooks", serviceType);
        service.setId(2L);

        DeviceType deviceType = new DeviceType(1L, "Windows Workstation");

        serviceCost = new ServiceCost(service, deviceType, BigDecimal.TEN);
        serviceCost.setId(1L);

        serviceCostRequest = new ServiceCostRequestDTO(serviceCost.getService().getId(), serviceCost.getDeviceType().getId(), serviceCost.getAmount());
    }

    @Test
    void create() throws Exception {
        when(serviceCostService.save(any())).thenReturn(serviceCost);

        mockMvc.perform(post(SERVICE_COSTS_RESOURCE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serviceCostRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(serviceCost.getId()))
                .andExpect(jsonPath("$.serviceId").value(serviceCost.getService().getId()))
                .andExpect(jsonPath("$.deviceTypeId").value(serviceCost.getDeviceType().getId()))
                .andExpect(jsonPath("$.amount").value(serviceCost.getAmount()));
    }

    @Test
    void createWithoutServiceId() throws Exception {
        serviceCostRequest.setServiceId(null);

        mockMvc.perform(post(SERVICE_COSTS_RESOURCE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serviceCostRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value(SERVICE_COSTS_RESOURCE))
                .andExpect(jsonPath("$.status").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void createWithoutDeviceTypeId() throws Exception {
        serviceCostRequest.setDeviceTypeId(null);

        mockMvc.perform(post(SERVICE_COSTS_RESOURCE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serviceCostRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value(SERVICE_COSTS_RESOURCE))
                .andExpect(jsonPath("$.status").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void createWithoutAmount() throws Exception {
        serviceCostRequest.setAmount(null);

        mockMvc.perform(post(SERVICE_COSTS_RESOURCE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serviceCostRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value(SERVICE_COSTS_RESOURCE))
                .andExpect(jsonPath("$.status").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void createNegativeAmount() throws Exception {
        serviceCostRequest.setAmount(new BigDecimal("-10"));

        mockMvc.perform(post(SERVICE_COSTS_RESOURCE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serviceCostRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value(SERVICE_COSTS_RESOURCE))
                .andExpect(jsonPath("$.status").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void serviceCostAlreadyExists() throws Exception {
        ServiceCostAlreadyExistsException exception = new ServiceCostAlreadyExistsException("Service cost already exists");
        when(serviceCostService.save(any())).thenThrow(exception);

        mockMvc.perform(post(SERVICE_COSTS_RESOURCE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serviceCostRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value(SERVICE_COSTS_RESOURCE))
                .andExpect(jsonPath("$.status").value(CONFLICT.value()))
                .andExpect(jsonPath("$.error").value(CONFLICT.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(exception.getMessage()));
    }

    @Test
    void findAll() throws Exception {
        when(serviceCostService.findAll()).thenReturn(List.of(serviceCost));

        mockMvc.perform(get(SERVICE_COSTS_RESOURCE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(serviceCost.getId()))
                .andExpect(jsonPath("$[0].serviceId").value(serviceCost.getService().getId()))
                .andExpect(jsonPath("$[0].deviceTypeId").value(serviceCost.getDeviceType().getId()))
                .andExpect(jsonPath("$[0].amount").value(serviceCost.getAmount()));
    }

    @Test
    void findById() throws Exception {
        when(serviceCostService.findById(serviceCost.getId())).thenReturn(serviceCost);

        mockMvc.perform(get(SERVICE_COSTS_RESOURCE + "/" + serviceCost.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(serviceCost.getId()))
                .andExpect(jsonPath("$.serviceId").value(serviceCost.getService().getId()))
                .andExpect(jsonPath("$.deviceTypeId").value(serviceCost.getDeviceType().getId()))
                .andExpect(jsonPath("$.amount").value(serviceCost.getAmount()));
    }

    @Test
    void findByIdNotFound() throws Exception {
        NotFoundException exception = new NotFoundException("Service cost not found");
        when(serviceCostService.findById(serviceCost.getId())).thenThrow(exception);

        String path = SERVICE_COSTS_RESOURCE + "/" + serviceCost.getId();

        mockMvc.perform(get(path))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value(path))
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(exception.getMessage()));
    }

    @Test
    void update() throws Exception {
        ServiceCostUpdateRequestDTO request = new ServiceCostUpdateRequestDTO(serviceCost.getAmount());

        mockMvc.perform(put(SERVICE_COSTS_RESOURCE + "/" + serviceCost.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateWithoutAmount() throws Exception {
        String path = SERVICE_COSTS_RESOURCE + "/" + serviceCost.getId();
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ServiceCostUpdateRequestDTO())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value(path))
                .andExpect(jsonPath("$.status").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void updateWithNegativeAmount() throws Exception {
        String path = SERVICE_COSTS_RESOURCE + "/" + serviceCost.getId();
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ServiceCostUpdateRequestDTO(new BigDecimal("-10")))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value(path))
                .andExpect(jsonPath("$.status").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete(SERVICE_COSTS_RESOURCE + "/" + serviceCost.getId()))
                .andExpect(status().isNoContent());
    }
}