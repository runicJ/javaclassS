package com.spring.javaclassS.service;

import java.util.List;

import com.spring.javaclassS.vo.UserVO;

public interface UserService {

	public List<UserVO> getUserList();  // 어디서나 사용 가능해야 하니까 public

	public int setUserDelete(int idx);  // 구현객체

	public int setUserInputOk(UserVO vo);

	//public List<UserVO> getUserSearchOk(String keyword);
	public List<UserVO> getUserIdSearch(String mid);

	public int setUserUpdateOk(UserVO vo);
}
