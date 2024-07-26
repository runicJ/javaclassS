package com.spring.javaclassS.vo;

import lombok.Data;

@Data
public class PhotoGalleryVO {
	private int idx;
	private String mid;
	private String part;
	private String title;
	private String content;
	private String thumbnail;
	private int photoCount;
	private String hostIp;
	private String pDate;
	private int goodCount;
	private int readNum;
	
	// photoReply2.sql
	private int replyIdx;
	//private String replyMid;
	private int photoIdx;
	//private String replyContent;
	private String prDate;
	
	private int replyCnt;	// 댓글 개수
}
