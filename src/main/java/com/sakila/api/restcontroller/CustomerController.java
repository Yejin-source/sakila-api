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

import com.sakila.api.dto.CustomerDto;
import com.sakila.api.entity.CustomerEntity;
import com.sakila.api.entity.CustomerMapping;
import com.sakila.api.service.CustomerService;

@RestController
@CrossOrigin
public class CustomerController {
	private CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	// 전체 조회
	@GetMapping("/customerList/{currentPage}")
	public ResponseEntity<Page<CustomerMapping>> customer(@PathVariable int currentPage) {
		return new ResponseEntity<Page<CustomerMapping>>(customerService.findAllBy(currentPage), HttpStatus.OK);
	}
	
	// 한 행 조회
	@GetMapping("/customerOne/{customerId}")
	public ResponseEntity<CustomerEntity> customerOne(@PathVariable int customerId) {
		return new ResponseEntity<CustomerEntity>(customerService.findById(customerId), HttpStatus.OK);
	}
	
	// 입력
	@PostMapping("/customer")
	public ResponseEntity<String> customer(@RequestBody CustomerDto customerDto) {
		customerService.save(customerDto);
		return new ResponseEntity<String>("입력 성공", HttpStatus.CREATED);
	}
	
	// 수정
	@PatchMapping("/customer")
	public ResponseEntity<String> updateCustomer(@RequestBody CustomerDto customerDto) {
		customerService.update(customerDto);
		return new ResponseEntity<String>("수정 성공", HttpStatus.OK);
	}
	
	// 삭제
	@DeleteMapping("/customer/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable int customerId) {
		
		boolean result = customerService.delete(customerId);
		if(result) {
			return new ResponseEntity<String>("삭제 성공", HttpStatus.OK);
		}
		return new ResponseEntity<String>("삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
