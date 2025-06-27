package com.sakila.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sakila.api.dto.AddressDto;
import com.sakila.api.entity.AddressEntity;
import com.sakila.api.repository.AddressRepository;

@Service
@Transactional
public class AddressService {
	private AddressRepository addressRepository;
	
	public AddressService(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}
	
	// 전체 조회
	public List<AddressEntity> findAll() {
		return addressRepository.findAll();
	}
	
	// 입력
	public void save(AddressDto addressDto) {
		// DTO -> Entity
		AddressEntity saveAddressEntity = new AddressEntity();
		saveAddressEntity.setAddress(addressDto.getAddress());
		addressRepository.save(saveAddressEntity);
	}
	
	// 수정
	public void update(AddressDto addressDto) {
		AddressEntity updateAddressEntity = addressRepository.findById(addressDto.getAddressId()).orElse(null);
		updateAddressEntity.setAddress(addressDto.getAddress());
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
