package com.ninjaone.rmmplatform.repository;

import com.ninjaone.rmmplatform.model.ServiceCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCostRepository extends JpaRepository<ServiceCost, Long> { }
