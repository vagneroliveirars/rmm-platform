package com.ninjaone.rmmplatform.repository;

import com.ninjaone.rmmplatform.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> { }
