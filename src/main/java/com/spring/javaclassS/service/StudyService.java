package com.spring.javaclassS.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.javaclassS.vo.CrimeVO;
import com.spring.javaclassS.vo.UserVO;

public interface StudyService {

	public String[] getCityStringArray(String dodo);

	public ArrayList<String> getCityArrayList(String dodo);

	public HashMap<String, UserVO> getUserMidMap();
    
	public UserVO getUserMidInfo(String mid);

	public ArrayList<String> getDbtestMidList();

	public UserVO getUserIdCheck(String mid);

	public ArrayList<String> getDbtestAddressList();

	public ArrayList<UserVO> getUserAddressCheck(String address);

	public UserVO getUserMidSearch(String mid);

	public ArrayList<UserVO> getUserMidList(String mid);

	public void setSaveCrimeData(CrimeVO vo);

	public int setDeleteCrimeData(int year);

	public String getListCrimeData(int year);

	public String getPoliceCrimeDate(String police, int year);

	public String getPoliceCheck(String police);
	
	public String getYearPoliceCheck(String police, int year, String sort);
}
