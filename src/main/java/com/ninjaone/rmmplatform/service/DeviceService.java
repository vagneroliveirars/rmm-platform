package com.ninjaone.rmmplatform.service;

import com.ninjaone.rmmplatform.controller.dto.DeviceRequestDTO;
import com.ninjaone.rmmplatform.exception.DeviceAlreadyExistsException;
import com.ninjaone.rmmplatform.exception.NotFoundException;
import com.ninjaone.rmmplatform.model.Device;
import com.ninjaone.rmmplatform.model.DeviceType;
import com.ninjaone.rmmplatform.model.Service;
import com.ninjaone.rmmplatform.repository.DeviceRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Set;

@org.springframework.stereotype.Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    private final DeviceTypeService deviceTypeService;

    private final ServiceService serviceService;

    public DeviceService(DeviceRepository deviceRepository, DeviceTypeService deviceTypeService, ServiceService serviceService) {
        this.deviceRepository = deviceRepository;
        this.deviceTypeService = deviceTypeService;
        this.serviceService = serviceService;
    }

    public Device save(DeviceRequestDTO request) {
        try {
            DeviceType deviceType = deviceTypeService.findById(request.getType());
            Device device = new Device(request.getUuid(), request.getSystemName(), deviceType);
            return deviceRepository.save(device);
        } catch (DataIntegrityViolationException exception) {
            throw new DeviceAlreadyExistsException(String.format("Device with UUID %s already exists", request.getUuid()), exception);
        }
    }

    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    public Device findById(Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Device not found with id %s", id)));
    }

    public void updateById(Long id, DeviceRequestDTO request) {
        try {
            Device device = findById(id);
            DeviceType deviceType = deviceTypeService.findById(request.getType());
            device.setUuid(request.getUuid());
            device.setType(deviceType);
            device.setSystemName(request.getSystemName());
            deviceRepository.save(device);
        } catch (DataIntegrityViolationException exception) {
            throw new DeviceAlreadyExistsException(String.format("Device with UUID %s already exists", request.getUuid()), exception);
        }
    }

    public void deleteById(Long id) {
        try {
            deviceRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException(String.format("Device not found with id %s", id), exception);
        }
    }

    public Set<Service> addService(Long id, Long serviceId) {
        Device device = findById(id);
        Service service = serviceService.findById(serviceId);
        if (device.getServices().add(service)) {
            deviceRepository.save(device);
        }
        return device.getServices();
    }

    public void removeService(Long id, Long serviceId) {
        Device device = findById(id);
        if (device.getServices().removeIf(service -> service.getId().equals(serviceId))) {
            deviceRepository.save(device);
        }
    }

}
