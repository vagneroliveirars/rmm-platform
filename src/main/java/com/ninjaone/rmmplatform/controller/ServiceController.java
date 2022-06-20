package com.ninjaone.rmmplatform.controller;

import com.ninjaone.rmmplatform.controller.dto.ServiceCostResponseDTO;
import com.ninjaone.rmmplatform.controller.dto.ServiceRequestDTO;
import com.ninjaone.rmmplatform.controller.dto.ServiceResponseDTO;
import com.ninjaone.rmmplatform.mapper.ServiceCostMapper;
import com.ninjaone.rmmplatform.mapper.ServiceMapper;
import com.ninjaone.rmmplatform.service.ServiceService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServiceService serviceService;

    private final ServiceMapper serviceMapper;

    private final ServiceCostMapper serviceCostMapper;

    public ServiceController(ServiceService serviceService, ServiceMapper serviceMapper, ServiceCostMapper serviceCostMapper) {
        this.serviceService = serviceService;
        this.serviceMapper = serviceMapper;
        this.serviceCostMapper = serviceCostMapper;
    }

    @GetMapping
    public List<ServiceResponseDTO> findAll() {
        return serviceService.findAll().stream().map(serviceMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ServiceResponseDTO findById(@PathVariable Long id) {
        return serviceMapper.toDTO(serviceService.findById(id));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ServiceResponseDTO create(@Valid @RequestBody ServiceRequestDTO request) {
        return serviceMapper.toDTO(serviceService.save(request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        serviceService.deleteById(id);
    }

    @GetMapping("/{id}/costs")
    public List<ServiceCostResponseDTO> getCostsById(@PathVariable Long id) {
        return serviceService.findById(id).getCosts().stream().map(serviceCostMapper::toDTO).toList();
    }

}
