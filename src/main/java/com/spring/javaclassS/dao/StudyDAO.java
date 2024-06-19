package com.spring.javaclassS.dao;

import java.util.ArrayList;
import java.util.List;

import com.spring.javaclassS.vo.UserVO;

public interface StudyDAO {
	public ArrayList<UserVO> getUserMidMap();
    
	//public UserVO getUserMidInfo(String mid);
}
