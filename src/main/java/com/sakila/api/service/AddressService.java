package com.sakila.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sakila.api.dto.AddressDto;
import com.sakila.api.entity.AddressEntity;
import com.sakila.api.entity.CityEntity;
import com.sakila.api.entity.CountryEntity;
import com.sakila.api.repository.AddressRepository;
import com.sakila.api.repository.CityRepository;

@Service
@Transactional
public class AddressService {
	private AddressRepository addressRepository;
	private CityRepository cityRepository;
	
	public AddressService(AddressRepository addressRepository, CityRepository cityRepository) {
		this.addressRepository = addressRepository;
		this.cityRepository = cityRepository;
	}
	
	// 전체 조회
	public List<AddressEntity> findAll() {
		return addressRepository.findAll();
	}
	
	// 한 행 조회
	public AddressEntity findById(int addressId) {
		return addressRepository.findById(addressId).orElse(null);
	}
	
	// 입력
	public void save(AddressDto addressDto) {
		// DTO -> Entity
		AddressEntity saveAddressEntity = new AddressEntity();
		saveAddressEntity.setAddress(addressDto.getAddress());
		saveAddressEntity.setAddress2(addressDto.getAddress2());
		saveAddressEntity.setDistrict(addressDto.getDistrict());
		saveAddressEntity.setPostalCode(addressDto.getPostalCode());
		saveAddressEntity.setPhone(addressDto.getPhone());
		
		// CityEntity
		CityEntity cityEntity = cityRepository.findById(addressDto.getCityId()).orElse(null);
		saveAddressEntity.setCityEntity(cityEntity);
		
		addressRepository.save(saveAddressEntity);
	}
	
	// 수정
	public void update(AddressDto addressDto) {
		AddressEntity updateAddressEntity = addressRepository.findById(addressDto.getAddressId()).orElse(null);
		
		if(updateAddressEntity != null) {
	        updateAddressEntity.setAddress(addressDto.getAddress());
	        updateAddressEntity.setAddress2(addressDto.getAddress2());
	        updateAddressEntity.setDistrict(addressDto.getDistrict());
	        updateAddressEntity.setPostalCode(addressDto.getPostalCode());
	        updateAddressEntity.setPhone(addressDto.getPhone());

	        CityEntity cityEntity = cityRepository.findById(addressDto.getCityId()).orElse(null);
	        if(cityEntity != null) {
	        	updateAddressEntity.setCityEntity(cityEntity);
	        }
	    }
	}
	
	// 삭제
	public boolean delete(int addressId) {
		if(addressRepository.existsById(addressId)) {
			addressRepository.deleteById(addressId);
			return true;
		}
		return false;
	}
}
