package com.spring.javaclassS.service;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.spring.javaclassS.vo.MemberVO;

public interface MemberService {


	public MemberVO getMemberIdCheck(String mid);

	public MemberVO getMemberNickCheck(String nickName);

	public int setMemberJoinOk(MultipartFile fName, MemberVO vo) throws IOException;

	public void setMemberPasswordUpdate(String mid, String pwd);

	public void setMemberInforUpdate(String mid, int point);

	public int setPwdChangeOk(String mid, String pwd);

	public ArrayList<MemberVO> getMemberList(int level);

	public int setMemberUpdateOk(MultipartFile fName, MemberVO vo);

	public int setMemberDelete(String mid);
}
