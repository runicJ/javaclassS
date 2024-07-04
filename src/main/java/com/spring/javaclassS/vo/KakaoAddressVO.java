package com.spring.javaclassS.vo;

import lombok.Data;

@Data  // constructor에선 에러남
public class KakaoAddressVO {
	private String address;
	private double latitude;
	private double longitude;
}
