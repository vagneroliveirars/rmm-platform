package com.ninjaone.rmmplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.rmmplatform.Application;
import com.ninjaone.rmmplatform.controller.dto.ServiceTypeRequestDTO;
import com.ninjaone.rmmplatform.exception.NotFoundException;
import com.ninjaone.rmmplatform.model.ServiceType;
import com.ninjaone.rmmplatform.service.ServiceTypeService;
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
class ServiceTypeControllerTest {

    private static final String SERVICE_TYPES_RESOURCE = "/service-types";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ServiceTypeService serviceTypeService;

    private ServiceType serviceType;

    @BeforeEach
    void setUp() {
        serviceType = new ServiceType(1L, "Device");
    }

    @Test
    void findAll() throws Exception {
        ServiceType deviceServiceType = new ServiceType(1L, "Device");
        ServiceType antiVirusServiceType = new ServiceType(1L, "Antivirus");
        List<ServiceType> types = List.of(deviceServiceType, antiVirusServiceType);

        when(serviceTypeService.findAll()).thenReturn(types);

        mockMvc.perform(get(SERVICE_TYPES_RESOURCE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(types)));
    }

    @Test
    void findById() throws Exception {
        when(serviceTypeService.findById(serviceType.getId())).thenReturn(serviceType);

        mockMvc.perform(get(SERVICE_TYPES_RESOURCE + "/" + serviceType.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(serviceType)));
    }

    @Test
    void findByIdNotFound() throws Exception {
        NotFoundException exception = new NotFoundException("Service type not found");
        when(serviceTypeService.findById(serviceType.getId())).thenThrow(exception);

        String path = SERVICE_TYPES_RESOURCE + "/" + serviceType.getId();

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
        ServiceTypeRequestDTO request = new ServiceTypeRequestDTO(serviceType.getDescription());

        when(serviceTypeService.save(any())).thenReturn(serviceType);

        mockMvc.perform(post(SERVICE_TYPES_RESOURCE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(serviceType)));
    }

    @Test
    void createWithoutDescription() throws Exception {
        mockMvc.perform(post(SERVICE_TYPES_RESOURCE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ServiceTypeRequestDTO())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value(SERVICE_TYPES_RESOURCE))
                .andExpect(jsonPath("$.status").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete(SERVICE_TYPES_RESOURCE + "/" + serviceType.getId()))
                .andExpect(status().isNoContent());
    }
}