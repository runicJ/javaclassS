package com.spring.javaclassS.vo;

import lombok.Data;

@Data
public class CrimeVO {
	private int idx;
	private int year;
	private String police;
	private int robbery;
	private int theft;
	private int murder;
	private int violence;	
}
