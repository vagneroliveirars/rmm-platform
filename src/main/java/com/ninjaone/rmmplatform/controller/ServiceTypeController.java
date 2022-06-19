package com.ninjaone.rmmplatform.controller;

import com.ninjaone.rmmplatform.controller.dto.ServiceTypeRequestDTO;
import com.ninjaone.rmmplatform.controller.dto.ServiceTypeResponseDTO;
import com.ninjaone.rmmplatform.mapper.ServiceTypeMapper;
import com.ninjaone.rmmplatform.service.ServiceTypeService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/service-types")
public class ServiceTypeController {

    private final ServiceTypeService service;

    private final ServiceTypeMapper mapper;

    public ServiceTypeController(ServiceTypeService service, ServiceTypeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ServiceTypeResponseDTO> findAll() {
        return service.findAll().stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ServiceTypeResponseDTO findById(@PathVariable Long id) {
        return mapper.toDTO(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ServiceTypeResponseDTO create(@Valid @RequestBody ServiceTypeRequestDTO request) {
        return mapper.toDTO(service.save(mapper.toEntity(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

}
