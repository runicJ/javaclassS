package com.spring.javaclassS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaclassS.dao.UserDAO;
import com.spring.javaclassS.vo.UserVO;

@Service
public class UserServiceImpl implements UserService {  // 인터페이스 구현객체
	
	@Autowired
	UserDAO userDAO;

	@Override
	public List<UserVO> getUserList() {
		//userDAO.getUserList();  // userDAO에 만들어줘
		return userDAO.getUserList();  // 바로 호출해서 작업하고 돌아오면 리턴해줘
	}

	@Override
	public int setUserDelete(int idx) {  // 받을때는 타입과 같이
		return userDAO.setUserDelete(idx);  // 보낼때는 변수만
	}

	@Override
	public int setUserInputOk(UserVO vo) {
		return userDAO.setUserInputOk(vo);
	}

//	@Override
//	public List<UserVO> getUserSearchOk(String keyword) {
//		return userDAO.getUserSearchOk(keyword);
//	}

	@Override
	public List<UserVO> getUserIdSearch(String mid) {
		return userDAO.getUserIdSearch(mid);
	}

	@Override
	public int setUserUpdateOk(UserVO vo) {
		return userDAO.setUserUpdateOk(vo);
	}
	
}
