package com.spring.javaclassS.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.javaclassS.vo.UserVO;

public interface StudyService {

	public String[] getCityStringArray(String dodo);

	public ArrayList<String> getCityArrayList(String dodo);

	public HashMap<String, UserVO> getUserMidMap();
    
	public UserVO getUserMidInfo(String mid);
}
