package com.sakila.api.entity;

public interface CustomerMapping {
	int getCustomerId();
	String getFirstName();
	String getLastName();
}

// 4개의 엔티티 전체 리스트
// -> 매핑타입의 전체 Page(리스트)로 변경
