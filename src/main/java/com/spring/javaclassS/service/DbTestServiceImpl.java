package com.spring.javaclassS.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaclassS.dao.DbTestDAO;
import com.spring.javaclassS.vo.UserVO;

@Service
public class DbTestServiceImpl implements DbTestService {

	@Autowired  // db값 얘가 안가져 오고 dao 시킴
	DbTestDAO dbTestDAO;

	@Override
	public ArrayList<UserVO> getDbTestList() {
		return dbTestDAO.getDbTestList();
	}

	@Override
	public ArrayList<UserVO> getDbTestSearch(String mid) {
		return dbTestDAO.getDbTestSearch(mid);
	}

	@Override
	public int setDbTestDelete(int idx) {
		return dbTestDAO.setDbTestDelete(idx);
	}

	@Override
	public int setDbTestInputOk(UserVO vo) {
		return dbTestDAO.setDbTestInputOk(vo);
	}

	@Override
	public int setDbTestUpdateOk(UserVO vo) {
		return dbTestDAO.setDbTestUpdateOk(vo);
	}

	@Override
	public UserVO getUserIdCheck(String mid) {
		return dbTestDAO.getUserIdCheck(mid);
	}
}
