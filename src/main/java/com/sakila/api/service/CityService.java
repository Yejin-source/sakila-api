package com.sakila.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sakila.api.dto.CityDto;
import com.sakila.api.entity.CityEntity;
import com.sakila.api.entity.CityMapping;
import com.sakila.api.entity.CountryEntity;
import com.sakila.api.repository.CityRepository;
import com.sakila.api.repository.CountryRepository;

@Service
@Transactional
public class CityService {
	private CityRepository cityRepository;
	private CountryRepository countryRepository;
	
	public CityService(CityRepository cityRepository, CountryRepository countryRepository) {
		this.cityRepository = cityRepository;
		this.countryRepository = countryRepository;
	}
	
	// 전체 조회
	public Page<CityMapping> findAllBy(int currentPage) {
		int pageSize = 10;
		int pageNumber = currentPage - 1;
		Sort sort = Sort.by("cityId").ascending();
		
		PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
		return cityRepository.findAllBy(pageable);
	}
	
	// 한 행 조회
	public CityEntity findById(int cityId) {
		return cityRepository.findById(cityId).orElse(null);
	}
	
	// 입력
	public void save(CityDto cityDto) {
		// DTO -> Entity
		CityEntity saveCityEntity = new CityEntity();
		saveCityEntity.setCity(cityDto.getCity());
		
		// CountryEntity
		CountryEntity countryEntity = countryRepository.findById(cityDto.getCountryId()).orElse(null);
		saveCityEntity.setCountryEntity(countryEntity);
		
		cityRepository.save(saveCityEntity);
	}
	
	// city 수정
	public void update(CityDto cityDto) {
		CityEntity updateCityEntity = cityRepository.findById(cityDto.getCityId()).orElse(null);
		
		if(updateCityEntity != null) {
	        updateCityEntity.setCity(cityDto.getCity());

	        CountryEntity countryEntity = countryRepository.findById(cityDto.getCountryId()).orElse(null);
	        if(countryEntity != null) {
	            updateCityEntity.setCountryEntity(countryEntity);
	        }
	    }
	}
	
	// city 삭제
	public boolean delete(int cityId) {
		if(cityRepository.existsById(cityId)) {
			cityRepository.deleteById(cityId);
			return true;
		}
		return false;
	}
}
