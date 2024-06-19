package com.spring.javaclassS.service;

import java.util.ArrayList;

import com.spring.javaclassS.vo.UserVO;

public interface DbTestService {

	public ArrayList<UserVO> getDbTestList();

	public ArrayList<UserVO> getDbTestSearch(String mid);

	public int setDbTestDelete(int idx);

	public int setDbTestInputOk(UserVO vo);

	public int setDbTestUpdateOk(UserVO vo);

	public UserVO getUserIdCheck(String mid);

}