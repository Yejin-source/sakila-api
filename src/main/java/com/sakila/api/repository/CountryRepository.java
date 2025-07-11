package com.sakila.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sakila.api.entity.CountryEntity;
import com.sakila.api.entity.CountryMapping;

public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {
	// List<CountryEntity> findAll();  // 기본 타입
	Page<CountryMapping> findAllBy(Pageable pageable);
}
