package com.spring.javaclassS.service;

import com.spring.javaclassS.vo.MemberVO;

public interface MemberService {

	public MemberVO getMemberIdCheck(String mid);

	public MemberVO getMemberNickCheck(String nickName);

	public int setMemberJoinOk(MemberVO vo);

	public MemberVO getMemberPasswordCheck(String mid, String email);

	public void setMemberPasswordUpdate(String mid, String pwd);

	
}
