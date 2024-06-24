package com.spring.javaclassS.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaclassS.vo.CrimeVO;
import com.spring.javaclassS.vo.UserVO;

public interface StudyDAO {
	public UserVO getUserMidSearch(@Param("mid") String mid);

	public ArrayList<UserVO> getUserMidList(@Param("mid") String mid);

	public void setSaveCrimeData(@Param("vo") CrimeVO vo);

	public void setDeleteCrimeDate(@Param("year") int year);

	public ArrayList<CrimeVO> getListCrimeDate(@Param("year") int year);

	public ArrayList<CrimeVO> getYearPoliceCheck(@Param("year") int year, @Param("police") String police, @Param("yearOrder") String yearOrder);

	public CrimeVO getAnalyzeTotal(@Param("year") int year, @Param("police") String police);

}
