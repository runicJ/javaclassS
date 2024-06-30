package com.spring.javaclassS.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javaclassS.vo.PdsReplyVO;
import com.spring.javaclassS.vo.PdsVO;

public interface PdsService {

	public List<PdsVO> getPdsList(int startIndexNo, int pageSize, String part);

	public int setPdsUpload(MultipartHttpServletRequest mFile, PdsVO vo);

	public int setPdsDownNumPlus(int idx);

	public int setPdsDelete(int idx, String fSName, HttpServletRequest request);

	public PdsVO getPdsContent(int idx);

	public int setPdsReplyInput(PdsReplyVO replyVO);

	public List<PdsReplyVO> getPdsReplyList(int idx);

	public int setPdsReplyDelete(int idx);

	public PdsReplyVO getPdsReplyCheck(int pdsIdx, String mid);
	
}
