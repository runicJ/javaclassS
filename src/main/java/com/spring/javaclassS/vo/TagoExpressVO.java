package com.spring.javaclassS.vo;

import lombok.Data;

@Data
public class TagoExpressVO {
	private String routeId;
	private String gradeNm;
	private int depPlandTime;
	private int arrPlandTime;
	private String depPlaceNm;
	private String arrPlaceNm;
	private int charge;
}
