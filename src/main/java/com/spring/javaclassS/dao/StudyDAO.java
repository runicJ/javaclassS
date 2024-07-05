package com.spring.javaclassS.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaclassS.vo.CrimeVO;
import com.spring.javaclassS.vo.KakaoAddressVO;
import com.spring.javaclassS.vo.PetCafeVO;
import com.spring.javaclassS.vo.UserVO;

public interface StudyDAO {
	
	public UserVO getUserMidSearch(@Param("mid") String mid);

	public ArrayList<UserVO> getUserMidList(@Param("mid") String mid);

	public void setSaveCrimeDate(@Param("vo") CrimeVO vo);

	public void setDeleteCrimeDate(@Param("year") int year);

	public ArrayList<CrimeVO> getListCrimeDate(@Param("year") int year);

	public ArrayList<CrimeVO> getYearPoliceCheck(@Param("year") int year, @Param("police") String police, @Param("yearOrder") String yearOrder);

	public CrimeVO getAnalyzeTotal(@Param("year") int year, @Param("police") String police);

	public KakaoAddressVO getKakaoAddressSearch(@Param("address") String address);

	public void setKakaoAddressInput(@Param("vo") KakaoAddressVO vo);

	public List<KakaoAddressVO> getKakaoAddressList();

	public int setKakaoAddressDelete(String address);

	public void setPetCafe(PetCafeVO vo);

}
