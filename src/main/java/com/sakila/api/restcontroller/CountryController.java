package com.sakila.api.restcontroller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sakila.api.dto.CountryDto;
import com.sakila.api.entity.CountryEntity;
import com.sakila.api.entity.CountryMapping;
import com.sakila.api.service.CountryService;

@RestController
@CrossOrigin  // 외부 출처의 요청을 허용하도록 설정
public class CountryController {
	private CountryService countryService;
	
	// 필드 주입 대신 생성자 주입을 사용
	public CountryController(CountryService countryService) {
		this.countryService = countryService;
	}
	
	// 전체 조회
	@GetMapping("/countryList/{currentPage}")
	public ResponseEntity<Page<CountryMapping>> country(@PathVariable int currentPage) {
		return new ResponseEntity<Page<CountryMapping>>(countryService.findAllBy(currentPage), HttpStatus.OK); 
	}
	
	// 한 행 조회
	@GetMapping("/countryOne/{countryId}")
	public ResponseEntity<CountryEntity> countryOne(@PathVariable int countryId) {
		return new ResponseEntity<CountryEntity>(countryService.findById(countryId), HttpStatus.OK);
	}
	
	// 입력
	@PostMapping("/country")
	public ResponseEntity<String> country(@RequestBody CountryDto countryDto) {
		// @RequestBody json 형태의 문자열 매개값을 CountryDto 타입으로 변환시킴
		System.out.println(countryDto.toString());
		countryService.save(countryDto);
		
		return new ResponseEntity<String>("입력 성공", HttpStatus.CREATED);
	}
	
	// 수정
	@PatchMapping("/country")
	public ResponseEntity<String> updateCountry(@RequestBody CountryDto countryDto) {
		countryService.update(countryDto);
		return new ResponseEntity<String>("수정 성공", HttpStatus.OK);
	}
	
	// 삭제
	@DeleteMapping("/country/{countryId}")
	public ResponseEntity<String> deleteCountry(@PathVariable int countryId) {
		
		boolean result = countryService.delete(countryId);
		if(result) {
			return new ResponseEntity<String>("삭제 성공", HttpStatus.OK);			
		}
		return new ResponseEntity<String>("삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
