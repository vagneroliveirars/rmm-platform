package com.ninjaone.rmmplatform.controller;

import com.ninjaone.rmmplatform.controller.dto.DeviceTypeRequestDTO;
import com.ninjaone.rmmplatform.controller.dto.DeviceTypeResponseDTO;
import com.ninjaone.rmmplatform.mapper.DeviceTypeMapper;
import com.ninjaone.rmmplatform.service.DeviceTypeService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/device-types")
public class DeviceTypeController {

    private final DeviceTypeService service;

    private final DeviceTypeMapper mapper;

    public DeviceTypeController(DeviceTypeService service, DeviceTypeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<DeviceTypeResponseDTO> findAll() {
        return service.findAll().stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public DeviceTypeResponseDTO findById(@PathVariable Long id) {
        return mapper.toDTO(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public DeviceTypeResponseDTO create(@Valid @RequestBody DeviceTypeRequestDTO request) {
        return mapper.toDTO(service.save(mapper.toEntity(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

}
