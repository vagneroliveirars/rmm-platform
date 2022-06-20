package com.ninjaone.rmmplatform.service;

import com.ninjaone.rmmplatform.controller.dto.ServiceCostRequestDTO;
import com.ninjaone.rmmplatform.exception.NotFoundException;
import com.ninjaone.rmmplatform.exception.ServiceCostAlreadyExistsException;
import com.ninjaone.rmmplatform.model.DeviceType;
import com.ninjaone.rmmplatform.model.Service;
import com.ninjaone.rmmplatform.model.ServiceCost;
import com.ninjaone.rmmplatform.repository.ServiceCostRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceCostService {

    private final ServiceCostRepository serviceCostRepository;

    private final ServiceService serviceService;

    private final DeviceTypeService deviceTypeService;

    public ServiceCostService(ServiceCostRepository serviceCostRepository, ServiceService serviceService, DeviceTypeService deviceTypeService) {
        this.serviceCostRepository = serviceCostRepository;
        this.serviceService = serviceService;
        this.deviceTypeService = deviceTypeService;
    }

    public ServiceCost save(ServiceCostRequestDTO request) {
        try {
            Service service = serviceService.findById(request.getServiceId());
            DeviceType deviceType = deviceTypeService.findById(request.getDeviceTypeId());
            return serviceCostRepository.save(new ServiceCost(service, deviceType, request.getAmount()));
        } catch (DataIntegrityViolationException exception) {
            throw new ServiceCostAlreadyExistsException(String.format("Service cost with service id %s and device type id %s already exists",
                    request.getServiceId(), request.getDeviceTypeId()), exception);
        }
    }

    public List<ServiceCost> findAll() {
        return serviceCostRepository.findAll();
    }

    public ServiceCost findById(Long id) {
        return serviceCostRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Service cost not found with id %s", id)));
    }

    public void update(Long id, BigDecimal amount) {
        ServiceCost serviceCost = findById(id);
        serviceCost.setAmount(amount);
        serviceCostRepository.save(serviceCost);
    }

    public void deleteById(Long id) {
        try {
            serviceCostRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException(String.format("Service cost not found with id %s", id));
        }
    }

}
