package com.spring.javaclassS.vo;

import lombok.Data;

@Data
public class BicycleVO {
	// 전국 자전거 대여점 현황
	private String bcyclLendNm;		// 자전거대여소명
	private String bcyclLendSe;		// 자전거대여소구분
	private String rdnmadr;				// 소재지도로명주소
	private String lnmadr;				// 소재지지번주소
	private String latitude;			// 위도
	private String longitude;			// 경도
	private String operOpenHm;		// 운영시작시각
	private String operCloseHm;		// 운영종료시각
	private String rstde;					// 휴무일
	private String chrgeSe;				// 요금구분
	private String bcyclUseCharge;	// 자전거이용요금
	private String bcyclHoldCharge;	// 자전거보유대수
	private String holderCo;				// 거치대수
	private String airInjectorYn;		// 공기주입기비치여부
	private String airInjectorType;	// 공기주입기유형
	private String repairStandYn;		// 수리대설치여부
	private String phoneNumber;			// 관리기관전화번호
	private String institutionNm;		// 관리기관명
	private String referenceDate;		// 데이터기준일자
	private String insttCode;			// 제공기관코드
	
	
	// 서울시 실시간 자전거 대여현황
	private String rackTotCnt;				// 거치대 개수
	private String stationName;				// 대여소 이름
	private String parkingBikeTotCnt;	// 자전거 주차 총건수
	private String shared;						// 거치율
	private String stationLatitude;		// 위도 
	private String stationLongitude;	// 경도
	private String stationId;					// 대여소 아이디
}
