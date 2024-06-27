package com.spring.javaclassS.vo;

import lombok.Data;

@Data
public class PdsVO {
	private int idx;
	private String mid;
	private String nickName; 
	private String fName;
	private String fSName;
	private int fSize;
	private String title;
	private String part;
	private String fDate;
	private int downNum;
	private String openSw;
	private String pwd;
	private String hostIp;
	private String content;
	
	private int hour_diff;  // 게시글 24시간 경과유무 체크하는 변수
	private int date_diff;  // 게시글을 일자 경과유무 체크변수
}
