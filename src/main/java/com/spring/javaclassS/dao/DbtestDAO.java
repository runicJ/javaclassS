package com.spring.javaclassS.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.spring.javaclassS.vo.UserVO;

public interface DbtestDAO {

	public ArrayList<UserVO> getDbtestList();  // 레파지토리 안쓰고 구현객체 사용 안하지만, 사실 얘가 저장소(레파지토리)역할

	// 이변수 받은 걸 "mid"로 받아서 맵퍼의 매개변수로 씀
	public ArrayList<UserVO> getDbtestSearch(@Param("mid") String mid);

	// xml에서 있는 변수명과 같으면 @Param 안써도 됨(vo는 당연히 생략 불가능)

	public int setDbtestDelete(@Param("idx") int idx);

	public int setDbtestInputOk(@Param("vo") UserVO vo);

	public int setDbtestUpdateOk(@Param("vo") UserVO vo);

	public UserVO getUserIdCheck(@Param("mid") String mid);

	public ArrayList<String> getDbtestMidList();

	public ArrayList<String> getDbtestAddressList();

	public ArrayList<UserVO> getUserAddressCheck(@Param("address") String address);

}
