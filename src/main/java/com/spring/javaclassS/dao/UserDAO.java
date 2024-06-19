package com.spring.javaclassS.dao;

import java.util.List;

import com.spring.javaclassS.vo.UserVO;

public interface UserDAO {

	public List<UserVO> getUserList();  // 제일 처음에만 public 붙여주면 다음부턴 자동으로 만들어짐

	public int setUserDelete(int idx);

	public int setUserInputOk(UserVO vo);

	//public List<UserVO> getUserSearchOk(String keyword);

	public List<UserVO> getUserIdSearch(String mid);

	public int setUserUpdateOk(UserVO vo);
		
}
