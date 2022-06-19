package com.ninjaone.rmmplatform.controller;

import com.ninjaone.rmmplatform.controller.dto.ServiceCostUpdateRequestDTO;
import com.ninjaone.rmmplatform.controller.dto.ServiceCostRequestDTO;
import com.ninjaone.rmmplatform.controller.dto.ServiceCostResponseDTO;
import com.ninjaone.rmmplatform.mapper.ServiceCostMapper;
import com.ninjaone.rmmplatform.service.ServiceCostService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/service-costs")
public class ServiceCostController {

    private final ServiceCostService serviceCostService;

    private final ServiceCostMapper serviceCostMapper;

    public ServiceCostController(ServiceCostService serviceCostService, ServiceCostMapper serviceCostMapper) {
        this.serviceCostService = serviceCostService;
        this.serviceCostMapper = serviceCostMapper;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ServiceCostResponseDTO create(@Valid @RequestBody ServiceCostRequestDTO request) {
        return serviceCostMapper.toDTO(serviceCostService.save(request));
    }

    @GetMapping
    public List<ServiceCostResponseDTO> findAll() {
        return serviceCostService.findAll().stream().map(serviceCostMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ServiceCostResponseDTO findById(@PathVariable Long id) {
        return serviceCostMapper.toDTO(serviceCostService.findById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody ServiceCostUpdateRequestDTO request) {
        serviceCostService.update(id, request.getAmount());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        serviceCostService.deleteById(id);
    }

}
