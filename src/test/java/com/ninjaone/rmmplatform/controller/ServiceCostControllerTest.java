package com.ninjaone.rmmplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.rmmplatform.Application;
import com.ninjaone.rmmplatform.controller.dto.ServiceCostRequestDTO;
import com.ninjaone.rmmplatform.controller.dto.ServiceCostUpdateRequestDTO;
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

    @BeforeEach
    void setUp() {
        ServiceType serviceType = new ServiceType(1L, "Device");
        Service service = new Service("Desktops and notebooks", serviceType);
        service.setId(2L);

        DeviceType deviceType = new DeviceType(1L, "Windows Workstation");

        serviceCost = new ServiceCost(service, deviceType, BigDecimal.TEN);
        serviceCost.setId(1L);
    }

    @Test
    void create() throws Exception {
        ServiceCostRequestDTO request = new ServiceCostRequestDTO(serviceCost.getService().getId(), serviceCost.getDeviceType().getId(), serviceCost.getAmount());

        when(serviceCostService.save(any())).thenReturn(serviceCost);

        mockMvc.perform(post(SERVICE_COSTS_RESOURCE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(serviceCost.getId()))
                .andExpect(jsonPath("$.serviceId").value(serviceCost.getService().getId()))
                .andExpect(jsonPath("$.deviceTypeId").value(serviceCost.getDeviceType().getId()))
                .andExpect(jsonPath("$.amount").value(serviceCost.getAmount()));
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
    void update() throws Exception {
        ServiceCostUpdateRequestDTO request = new ServiceCostUpdateRequestDTO(serviceCost.getAmount());

        mockMvc.perform(put(SERVICE_COSTS_RESOURCE + "/" + serviceCost.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete(SERVICE_COSTS_RESOURCE + "/" + serviceCost.getId()))
                .andExpect(status().isNoContent());
    }
}