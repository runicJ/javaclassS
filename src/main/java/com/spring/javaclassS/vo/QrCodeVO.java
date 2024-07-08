package com.spring.javaclassS.vo;

import lombok.Data;

@Data
public class QrCodeVO {
	
	private int idx;
	
	// 개인정보를 담을 필드
	private String mid;
	private String name;
	private String email;
	
	// 소개하고 싶은 사이트 필드
	private String moveUrl;
	
	// 티켓 정보를 담는 필드
	private String movieName;
	private String movieDate;
	private String movieTime;
	private int movieAdult;
	private int movieChild;

	// DB에 저장(검색)된 정보 확인하기 위한 필드
	private String publishDate;
	private String qrCodeName;
	
}
