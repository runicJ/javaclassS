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
	
	public void saveCrimeData(@Param("crimeVO") CrimeVO crimeVO);

//	public int setDeleteCrimeData(@Param("year") int year);
//	public List<CrimeVO> getListCrimeData(@Param("year") int year);
//	public List<CrimeVO> getPoliceCrimeDate(@Param("police") String police, @Param("year") int year);
//	public List<CrimeVO> getPoliceCheck(@Param("police") String police);
//	public List<CrimeVO> getYearPoliceCheckA(@Param("police") String police, @Param("year") int year);
//	public List<CrimeVO> getYearPoliceCheckD(@Param("police") String police, @Param("year") int year);
	
	public void setDeleteCrimeDate(@Param("year") int year);

	public ArrayList<CrimeVO> getListCrimeDate(@Param("year") int year);

	public ArrayList<CrimeVO> getYearPoliceCheck(@Param("year") int year, @Param("police") String police, @Param("yearOrder") String yearOrder);

	public CrimeVO getAnalyzeTotal(@Param("year") int year, @Param("police") String police);

}
