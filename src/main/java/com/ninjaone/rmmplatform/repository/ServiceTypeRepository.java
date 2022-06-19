package com.ninjaone.rmmplatform.repository;

import com.ninjaone.rmmplatform.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> { }
