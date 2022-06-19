package com.ninjaone.rmmplatform.service;

import com.ninjaone.rmmplatform.exception.NotFoundException;
import com.ninjaone.rmmplatform.model.ServiceType;
import com.ninjaone.rmmplatform.repository.ServiceTypeRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceTypeService {

    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeService(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public ServiceType save(ServiceType serviceType) {
        return serviceTypeRepository.save(serviceType);
    }

    public List<ServiceType> findAll() {
        return serviceTypeRepository.findAll();
    }

    public ServiceType findById(Long id) {
        return serviceTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Service type not found with id %s", id)));
    }

    public void deleteById(Long id) {
        try {
            serviceTypeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException(String.format("Service type not found with id %s", id));
        }
    }

}
