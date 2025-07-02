package com.sakila.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sakila.api.entity.AddressEntity;
import com.sakila.api.entity.AddressMapping;

public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {
	// JPA 기본 제공
	// List<AddressEntity> findAll();
	// AddressEntity save(AddressEntity address);
	
	Page<AddressMapping> findAllBy(Pageable pageable);
}
