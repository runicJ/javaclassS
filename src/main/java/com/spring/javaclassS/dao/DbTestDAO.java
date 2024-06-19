package com.spring.javaclassS.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.spring.javaclassS.vo.UserVO;

public interface DbTestDAO {

	public ArrayList<UserVO> getDbTestList();  // 레파지토리 안쓰고 구현객체 사용 안하지만, 사실 얘가 저장소(레파지토리)역할

	// 이변수 받은 걸 "mid"로 받아서 맵퍼의 매개변수로 씀
	public ArrayList<UserVO> getDbTestSearch(@Param("mid") String mid);

	// xml에서 있는 변수명과 같으면 @Param 안써도 됨(vo는 당연히 생략 불가능)
	public int setDbTestDelete(int idx);

	public int setDbTestInputOk(@Param("vo") UserVO vo);

	public int setDbTestUpdateOk(@Param("vo") UserVO vo);

	public UserVO getUserIdCheck(@Param("mid") String mid);


}
