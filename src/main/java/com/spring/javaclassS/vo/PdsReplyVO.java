package com.spring.javaclassS.vo;

import lombok.Data;

@Data
public class PdsReplyVO {
	private int idx;
	private int pdsIdx;
	private String mid;
	private String nickName;
	private String rDate;
	private int star;
	private String hostIp;
	private String content;
	private Integer parentId;
}
