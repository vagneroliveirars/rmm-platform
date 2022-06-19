package com.ninjaone.rmmplatform.service;

import com.ninjaone.rmmplatform.controller.dto.ServiceRequestDTO;
import com.ninjaone.rmmplatform.exception.NotFoundException;
import com.ninjaone.rmmplatform.exception.ServiceAlreadyExistsException;
import com.ninjaone.rmmplatform.model.Service;
import com.ninjaone.rmmplatform.model.ServiceType;
import com.ninjaone.rmmplatform.repository.ServiceRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceTypeService serviceTypeService;

    public ServiceService(ServiceRepository serviceRepository, ServiceTypeService serviceTypeService) {
        this.serviceRepository = serviceRepository;
        this.serviceTypeService = serviceTypeService;
    }

    public Service save(ServiceRequestDTO request) {
        try {
            ServiceType serviceType = serviceTypeService.findById(request.getType());
            Service service = new Service(request.getDescription(), serviceType);
            return serviceRepository.save(service);
        } catch (DataIntegrityViolationException exception) {
            throw new ServiceAlreadyExistsException(String.format("Service with type %s already exists", request.getType()));
        }
    }

    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    public Service findById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Service not found with id %s", id)));
    }

    public void deleteById(Long id) {
        try {
            serviceRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException(String.format("Service not found with id %s", id));
        }
    }

}
