package com.spring.javaclassS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaclassS.vo.PdsReplyVO;
import com.spring.javaclassS.vo.PdsVO;

public interface PdsDAO {
	
	public List<PdsVO> getPdsList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("part") String part);

	public int setPdsUpload(@Param("vo") PdsVO vo);

	public int totRecCnt(@Param("part") String part);

	public int setPdsDownNumPlus(@Param("idx") int idx);

	public int setPdsDelete(@Param("idx") int idx);

	public PdsVO getPdsContent(@Param("idx") int idx);

	public PdsReplyVO getPdsReplyCheck(@Param("pdsIdx") int pdsIdx, @Param("mid") String mid);

	public int setPdsReplyInput(@Param("replyVO") PdsReplyVO replyVO);

	public List<PdsReplyVO> getPdsReplyList(@Param("pdsIdx") int pdsIdx);

	public int setPdsReplyDelete(@Param("idx") int idx);
	
}
