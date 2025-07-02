package com.sakila.api.entity;

// CountryEntity 매핑 -> CountryEntity GETTER 생성 (필드 일부 읽기 전용 타입)
public interface CountryMapping {
	int getCountryId();  // CountryEntity GETTER만 사용가능
	String getCountry();
}
