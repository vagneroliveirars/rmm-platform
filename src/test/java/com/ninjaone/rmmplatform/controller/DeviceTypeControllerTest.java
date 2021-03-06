package com.ninjaone.rmmplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.rmmplatform.Application;
import com.ninjaone.rmmplatform.controller.dto.DeviceTypeRequestDTO;
import com.ninjaone.rmmplatform.exception.NotFoundException;
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
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
    void findByIdNotFound() throws Exception {
        NotFoundException exception = new NotFoundException("Device type not found");
        when(service.findById(deviceType.getId())).thenThrow(exception);

        String path = DEVICE_TYPES_RESOURCE + "/" + deviceType.getId();
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
    void createWithoutDescription() throws Exception {
        mockMvc.perform(post(DEVICE_TYPES_RESOURCE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new DeviceTypeRequestDTO())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value(DEVICE_TYPES_RESOURCE))
                .andExpect(jsonPath("$.status").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete(DEVICE_TYPES_RESOURCE + "/" + deviceType.getId()))
                .andExpect(status().isNoContent());
    }
}