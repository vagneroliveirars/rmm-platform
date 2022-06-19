package com.ninjaone.rmmplatform.repository;

import com.ninjaone.rmmplatform.model.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceTypeRepository extends JpaRepository<DeviceType, Long> { }
