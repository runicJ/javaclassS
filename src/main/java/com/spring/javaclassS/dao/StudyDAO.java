package com.spring.javaclassS.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaclassS.vo.CrimeVO;
import com.spring.javaclassS.vo.UserVO;

public interface StudyDAO {
	public ArrayList<UserVO> getUserMidMap();
    
	//public UserVO getUserMidInfo(String mid);
	
	public ArrayList<UserVO> getUserAddressCheck(@Param("address") String address);
	
	public UserVO getUserMidSearch(@Param("mid") String mid);

	public ArrayList<String> getDbtestMidList();

	public ArrayList<String> getDbtestAddressList();
	
	public ArrayList<UserVO> getUserMidList(@Param("mid") String mid);

	public void setSaveCrimeData(@Param("vo") CrimeVO vo);

}
