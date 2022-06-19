package com.ninjaone.rmmplatform.controller;

import com.ninjaone.rmmplatform.controller.dto.*;
import com.ninjaone.rmmplatform.mapper.DeviceMapper;
import com.ninjaone.rmmplatform.mapper.ServiceMapper;
import com.ninjaone.rmmplatform.service.DeviceService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;

    private final DeviceMapper deviceMapper;

    private final ServiceMapper serviceMapper;

    public DeviceController(DeviceService deviceService, DeviceMapper deviceMapper, ServiceMapper serviceMapper) {
        this.deviceService = deviceService;
        this.deviceMapper = deviceMapper;
        this.serviceMapper = serviceMapper;
    }

    @GetMapping
    public DevicesResponseDTO findAll() {
        return deviceService
                .findAll()
                .stream()
                .map(deviceMapper::toDTO)
                .collect(collectingAndThen(toList(), DevicesResponseDTO::new));
    }

    @GetMapping("/{id}")
    public DeviceResponseDTO findById(@PathVariable Long id) {
        return deviceMapper.toDTO(deviceService.findById(id));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public DeviceResponseDTO create(@Valid @RequestBody DeviceRequestDTO request) {
        return deviceMapper.toDTO(deviceService.save(request));
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateById(@PathVariable Long id, @Valid @RequestBody DeviceRequestDTO request) {
        deviceService.updateById(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        deviceService.deleteById(id);
    }

    @GetMapping("/{id}/services")
    public List<ServiceResponseDTO> getServices(@PathVariable Long id) {
        return deviceService.getServices(id).stream().map(serviceMapper::toDTO).toList();
    }

    @PostMapping("/{id}/services")
    public List<ServiceResponseDTO> addService(@PathVariable Long id, @Valid @RequestBody AddServiceRequestDTO request) {
        return deviceService.addService(id, request.getServiceId()).stream().map(serviceMapper::toDTO).toList();
    }

    @DeleteMapping("/{id}/services/{serviceId}")
    @ResponseStatus(NO_CONTENT)
    public void removeService(@PathVariable Long id, @PathVariable Long serviceId) {
        deviceService.removeService(id, serviceId);
    }

}
