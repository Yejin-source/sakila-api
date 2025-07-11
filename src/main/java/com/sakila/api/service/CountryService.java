package com.sakila.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sakila.api.dto.CountryDto;
import com.sakila.api.entity.CountryEntity;
import com.sakila.api.entity.CountryMapping;
import com.sakila.api.repository.CityRepository;
import com.sakila.api.repository.CountryRepository;

@Service
@Transactional
public class CountryService {
	private CountryRepository countryRepository;
	private CityRepository cityRepository;	

	// 필드 주입 대신 생성자 주입을 사용
	public CountryService(CountryRepository countryRepository, CityRepository cityRepository) {
		this.countryRepository = countryRepository;
		this.cityRepository = cityRepository;
	}
		
	// 전체 조회
	public Page<CountryMapping> findAllBy(int currentPage) {
		int pageSize = 10;
		int pageNumber = currentPage - 1;
		Sort sort = Sort.by("countryId").descending();
		
		PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
		return countryRepository.findAllBy(pageable);
	}
	
	// 한 행 조회
	public CountryEntity findById(int countryId) {
		return countryRepository.findById(countryId).orElse(null);
	}
	
	// CountryEntity 입력
	public void save(CountryDto countryDto) {
		// DTO -> Entity
		CountryEntity saveCountryEntity = new CountryEntity();
		saveCountryEntity.setCountry(countryDto.getCountry());
		countryRepository.save(saveCountryEntity);
	}
	
	// country 수정
	public void update(CountryDto countryDto) {
		CountryEntity updateCountryEntity = countryRepository.findById(countryDto.getCountryId()).orElse(null);
		updateCountryEntity.setCountry(countryDto.getCountry());
	}
	
	// country 삭제
	public boolean delete(int countryId) {
		// 이슈: 자식 테이블에 외래키로 참조하는 행이 있다면
		// 자식 테이블에 참조하는 행이 없다면 (select count(*) from city where country_id = ?)
		
		if(0 == cityRepository.countByCountryEntity(countryRepository.findById(countryId).orElse(null))) {
			countryRepository.deleteById(countryId);
			return true;
		} 
		return false;
	}
}
