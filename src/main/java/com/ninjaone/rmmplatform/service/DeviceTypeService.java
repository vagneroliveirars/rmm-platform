package com.ninjaone.rmmplatform.service;

import com.ninjaone.rmmplatform.exception.NotFoundException;
import com.ninjaone.rmmplatform.model.DeviceType;
import com.ninjaone.rmmplatform.repository.DeviceTypeRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceTypeService {

    private final DeviceTypeRepository deviceTypeRepository;

    public DeviceTypeService(DeviceTypeRepository deviceTypeRepository) {
        this.deviceTypeRepository = deviceTypeRepository;
    }

    public DeviceType save(DeviceType deviceType) {
        return deviceTypeRepository.save(deviceType);
    }

    public List<DeviceType> findAll() {
        return deviceTypeRepository.findAll();
    }

    public DeviceType findById(Long id) {
        return deviceTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Device type not found with id %s", id)));
    }

    public void deleteById(Long id) {
        try {
            deviceTypeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException(String.format("Device type not found with id %s", id));
        }
    }

}
