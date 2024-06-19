package com.spring.javaclassS.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.spring.javaclassS.vo.UserVO;

public interface StudyService {

	public String[] getCityStringArray(String dodo);

	public ArrayList<String> getCityArrayList(String dodo);

	public ArrayList<String> getUserMidList();

}
