package com.ninjaone.rmmplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.rmmplatform.Application;
import com.ninjaone.rmmplatform.controller.dto.DeviceTypeRequestDTO;
import com.ninjaone.rmmplatform.model.DeviceType;
import com.ninjaone.rmmplatform.service.DeviceTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
class DeviceTypeControllerTest {

    private static final String DEVICE_TYPES_RESOURCE = "/device-types";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeviceTypeService service;

    private DeviceType deviceType;

    @BeforeEach
    void setUp() {
        deviceType = new DeviceType(1L, "Windows Workstation");
    }

    @Test
    void findAll() throws Exception {
        DeviceType windows = new DeviceType(1L, "Windows Workstation");
        DeviceType mac = new DeviceType(2L, "Mac");
        List<DeviceType> devices = List.of(windows, mac);

        when(service.findAll()).thenReturn(devices);

        mockMvc.perform(get(DEVICE_TYPES_RESOURCE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(devices)));
    }

    @Test
    void findById() throws Exception {
        when(service.findById(deviceType.getId())).thenReturn(deviceType);

        mockMvc.perform(get(DEVICE_TYPES_RESOURCE + "/" + deviceType.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(deviceType)));
    }

    @Test
    void create() throws Exception {
        DeviceTypeRequestDTO request = new DeviceTypeRequestDTO(deviceType.getDescription());

        when(service.save(any())).thenReturn(deviceType);

        mockMvc.perform(post(DEVICE_TYPES_RESOURCE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(deviceType)));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete(DEVICE_TYPES_RESOURCE + "/" + deviceType.getId()))
                .andExpect(status().isNoContent());
    }
}