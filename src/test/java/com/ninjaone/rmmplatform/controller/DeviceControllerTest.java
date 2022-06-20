package com.ninjaone.rmmplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.rmmplatform.Application;
import com.ninjaone.rmmplatform.controller.dto.AddServiceRequestDTO;
import com.ninjaone.rmmplatform.controller.dto.DeviceRequestDTO;
import com.ninjaone.rmmplatform.model.Device;
import com.ninjaone.rmmplatform.model.DeviceType;
import com.ninjaone.rmmplatform.model.Service;
import com.ninjaone.rmmplatform.model.ServiceType;
import com.ninjaone.rmmplatform.service.DeviceService;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
class DeviceControllerTest {

    private static final String DEVICES_RESOURCE = "/devices";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeviceService deviceService;

    private Device device;

    private DeviceRequestDTO deviceRequest;

    @BeforeEach
    void setUp() {
        DeviceType deviceType = new DeviceType(1L, "Windows Workstation");
        device = new Device(UUID.randomUUID(), "Windows 10", deviceType);
        device.setId(2L);

        deviceRequest = new DeviceRequestDTO(device.getUuid(), device.getSystemName(), device.getType().getId());
    }

    @Test
    void findAll() throws Exception {
        List<Device> devices = List.of(device);
        when(deviceService.findAll()).thenReturn(devices);

        mockMvc.perform(get(DEVICES_RESOURCE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.devices").isArray())
                .andExpect(jsonPath("$.devices[0].id").value(device.getId()))
                .andExpect(jsonPath("$.devices[0].uuid").value(device.getUuid().toString()))
                .andExpect(jsonPath("$.devices[0].systemName").value(device.getSystemName()))
                .andExpect(jsonPath("$.devices[0].type.id").value(device.getType().getId()))
                .andExpect(jsonPath("$.devices[0].type.description").value(device.getType().getDescription()))
                .andExpect(jsonPath("$.devices[0].services").isEmpty())
                .andExpect(jsonPath("$.devices[0].totalMonthlyCost").value(BigDecimal.ZERO))
                .andExpect(jsonPath("$.totalMonthlyCost").value(BigDecimal.ZERO));
    }

    @Test
    void findById() throws Exception {
        when(deviceService.findById(device.getId())).thenReturn(device);

        mockMvc.perform(get(DEVICES_RESOURCE + "/" + device.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(device.getId()))
                .andExpect(jsonPath("$.uuid").value(device.getUuid().toString()))
                .andExpect(jsonPath("$.systemName").value(device.getSystemName()))
                .andExpect(jsonPath("$.type.id").value(device.getType().getId()))
                .andExpect(jsonPath("$.type.description").value(device.getType().getDescription()))
                .andExpect(jsonPath("$.services").isEmpty())
                .andExpect(jsonPath("$.totalMonthlyCost").value(BigDecimal.ZERO));
    }

    @Test
    void create() throws Exception {
        when(deviceService.save(any())).thenReturn(device);

        mockMvc.perform(post(DEVICES_RESOURCE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(device.getId()))
                .andExpect(jsonPath("$.uuid").value(device.getUuid().toString()))
                .andExpect(jsonPath("$.systemName").value(device.getSystemName()))
                .andExpect(jsonPath("$.type.id").value(device.getType().getId()))
                .andExpect(jsonPath("$.type.description").value(device.getType().getDescription()))
                .andExpect(jsonPath("$.services").isEmpty())
                .andExpect(jsonPath("$.totalMonthlyCost").value(BigDecimal.ZERO));
    }

    @Test
    void updateById() throws Exception {
        mockMvc.perform(put(DEVICES_RESOURCE + "/" + device.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete(DEVICES_RESOURCE + "/" + device.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getServices() throws Exception {
        ServiceType serviceType = new ServiceType(1L, "Device");
        Service service = new Service("Desktops and notebooks", serviceType);
        service.setId(2L);
        device.setServices(Set.of(service));

        when(deviceService.findById(device.getId())).thenReturn(device);

        mockMvc.perform(get(DEVICES_RESOURCE + "/" + device.getId() + "/services"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(service.getId()))
                .andExpect(jsonPath("$[0].description").value(service.getDescription()))
                .andExpect(jsonPath("$[0].type.id").value(serviceType.getId()))
                .andExpect(jsonPath("$[0].type.description").value(serviceType.getDescription()));
    }

    @Test
    void addService() throws Exception {
        ServiceType serviceType = new ServiceType(1L, "Device");
        Service service = new Service("Desktops and notebooks", serviceType);
        service.setId(2L);
        Set<Service> services = Set.of(service);

        AddServiceRequestDTO addServiceRequest = new AddServiceRequestDTO(service.getId());

        when(deviceService.addService(device.getId(), addServiceRequest.getServiceId())).thenReturn(services);

        mockMvc.perform(post(DEVICES_RESOURCE + "/" + device.getId() + "/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addServiceRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(service.getId()))
                .andExpect(jsonPath("$[0].description").value(service.getDescription()))
                .andExpect(jsonPath("$[0].type.id").value(serviceType.getId()))
                .andExpect(jsonPath("$[0].type.description").value(serviceType.getDescription()));
    }

    @Test
    void removeService() throws Exception {
        Long serviceId = 1L;
        mockMvc.perform(delete(DEVICES_RESOURCE + "/" + device.getId() + "/services/" + serviceId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}