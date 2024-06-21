package com.spring.javaclassS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaclassS.dao.MemberDAO;
import com.spring.javaclassS.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberDAO memberDAO;

	@Override
	public MemberVO getMemberIdCheck(String mid) {
		return memberDAO.getMemberIdCheck(mid);
	}

	@Override
	public MemberVO getMemberNickCheck(String nickName) {
		return memberDAO.getMemberNickCheck(nickName);
	}

	@Override
	public int setMemberJoinOk(MemberVO vo) {
		
		// 사진 처리
		vo.setPhoto("noImage.jpg");
		
		return memberDAO.setMemberJoinOk(vo);
	}

	@Override
	public MemberVO getMemberPasswordCheck(String mid, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMemberPasswordUpdate(String mid, String pwd) {
		// TODO Auto-generated method stub
		
	}
	
}
