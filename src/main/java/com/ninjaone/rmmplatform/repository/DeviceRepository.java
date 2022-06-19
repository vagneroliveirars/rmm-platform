package com.ninjaone.rmmplatform.repository;

import com.ninjaone.rmmplatform.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> { }
