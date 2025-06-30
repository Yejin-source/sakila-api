package com.sakila.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sakila.api.dto.CustomerDto;
import com.sakila.api.dto.StoreDto;
import com.sakila.api.entity.AddressEntity;
import com.sakila.api.entity.StoreEntity;
import com.sakila.api.repository.AddressRepository;
import com.sakila.api.repository.StoreRepository;

@Service
@Transactional
public class StoreService {
	private StoreRepository storeRepository;
	private AddressRepository addressRepository;
	
	public StoreService(StoreRepository storeRepository, AddressRepository addressRepository) {
		// 주입 전에 선행 작업 or 테스트, ...
		this.storeRepository = storeRepository;
		this.addressRepository = addressRepository;
	}
	
	// 전체 조회
	public List<StoreEntity> findAll() {
		return storeRepository.findAll();
	}

	// 한 행 조회
	public StoreEntity findById(int storeId) {
		return storeRepository.findById(storeId).orElse(null);
	}

	// 입력
	public void save(StoreDto storeDto) {
		// DTO -> Entity
		StoreEntity saveStoreEntity = new StoreEntity();
		saveStoreEntity.setManagerStaffId(storeDto.getManagerStaffId());
		
		// AddressEntity
		AddressEntity addressEntity = addressRepository.findById(storeDto.getAddressId()).orElse(null);
		saveStoreEntity.setAddressEntity(addressEntity);
		
		storeRepository.save(saveStoreEntity);
	}

	// 수정
	public void update(StoreDto storeDto) {
		StoreEntity updateStoreEntity = storeRepository.findById(storeDto.getStoreId()).orElse(null);
		
		if(updateStoreEntity != null) {
			updateStoreEntity.setManagerStaffId(storeDto.getManagerStaffId());
			
			AddressEntity addressEntity = addressRepository.findById(storeDto.getAddressId()).orElse(null);
			if(addressEntity != null) {
				updateStoreEntity.setAddressEntity(addressEntity);
			}
		}
	}

	// 삭제
	public boolean delete(int storeId) {
		if(storeRepository.existsById(storeId)) {
			storeRepository.deleteById(storeId);
			return true;
		}
		return false;
	}
}
