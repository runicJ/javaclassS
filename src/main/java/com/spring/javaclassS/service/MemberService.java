package com.spring.javaclassS.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.spring.javaclassS.vo.MemberVO;

public interface MemberService {

	public MemberVO getMemberIdCheck(String mid);

	public MemberVO getMemberNickCheck(String nickName);

	public int setMemberJoinOk(MemberVO vo);

	public void setMemberPasswordUpdate(String mid, String pwd);

	public void setMemberInforUpdate(String mid, int point);

	public int setPwdChangeOk(String mid, String pwd);

	public String fileUpload(MultipartFile fName, String mid, String photo);

	public ArrayList<MemberVO> getMemberList(int level);

	public int setMemberUpdateOk(MemberVO vo);

	public int setUserDel(String mid);

	public MemberVO getMemberNameCheck(String name);
	
}
