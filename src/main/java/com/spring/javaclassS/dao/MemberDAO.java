package com.spring.javaclassS.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javaclassS.vo.MemberVO;

public interface MemberDAO {

	public MemberVO getMemberIdCheck(@Param("mid") String mid);

	public MemberVO getMemberNickCheck(@Param("nickName") String nickName);

	public int setMemberJoinOk(@Param("vo") MemberVO vo);

	public void setMemberPasswordUpdate(@Param("mid") String mid, @Param("pwd") String pwd);

	public void setMemberInforUpdate(@Param("mid") String mid, @Param("point") int point);

	public int setPwdChangeOk(@Param("mid") String mid, @Param("pwd") String pwd);

	public ArrayList<MemberVO> getMemberList(@Param("level") int level);

	public int setMemberUpdateOk(@Param("vo") MemberVO vo);

	public int setUserDel(@Param("mid") String mid);

	public MemberVO getMemberNameCheck(@Param("name") String name);

	public MemberVO getMemberNickNameEmailCheck(@Param("nickName") String nickName, @Param("email") String email);

	public void setKakaoMemberInput(@Param("mid") String mid, @Param("pwd") String pwd, @Param("nickName") String nickName, @Param("email") String email);
	
}