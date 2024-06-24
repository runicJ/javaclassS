package com.spring.javaclassS.service;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javaclassS.common.JavaclassProvide;
import com.spring.javaclassS.dao.MemberDAO;
import com.spring.javaclassS.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDAO memberDAO;

	@Autowired
	JavaclassProvide javaclassProvide; 
	
	@Override
	public MemberVO getMemberIdCheck(String mid) {
		return memberDAO.getMemberIdCheck(mid);
	}

	@Override
	public MemberVO getMemberNickCheck(String nickName) {
		return memberDAO.getMemberNickCheck(nickName);
	}

	@Override
	public int setMemberJoinOk(MultipartFile fName, MemberVO vo) throws IOException {
		// 사진처리
		javaclassProvide.writeFile(fName, vo.getPhoto(), "member");
		
		return memberDAO.setMemberJoinOk(vo);
	}

	@Override
	public void setMemberPasswordUpdate(String mid, String pwd) {
		memberDAO.setMemberPasswordUpdate(mid, pwd);
	}

	@Override
	public void setMemberInforUpdate(String mid, int point) {
		memberDAO.setMemberInforUpdate(mid, point);
	}

	@Override
	public int setPwdChangeOk(String mid, String pwd) {
		return memberDAO.setPwdChangeOk(mid, pwd);
	}

	@Override
	public ArrayList<MemberVO> getMemberList(int level) {
		return memberDAO.getMemberList(level);
	}

	@Override
	public int setMemberUpdateOk(MultipartFile fName, MemberVO vo) {
		return memberDAO.setMemberUpdateOk(fName, vo);
	}

	@Override
	public int setMemberDelete(String mid) {
		return memberDAO.setMemberDelete(mid);
	}

}
