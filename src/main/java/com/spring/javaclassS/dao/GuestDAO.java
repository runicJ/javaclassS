package com.spring.javaclassS.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.spring.javaclassS.vo.GuestVO;

public interface GuestDAO {

	public ArrayList<GuestVO> getGuestList();

	public int setGuestInput(@Param("vo") GuestVO vo);

}