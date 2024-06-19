package com.spring.javaclassS.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.javaclassS.vo.UserVO;

@Repository("userDAO")  // 이건 저장소야 지정(mapper에서 가져오게 시킴)  // userDAO 이름이 맞아야기 가져가는 것 // sqlSessionFactory 사용
public class UserDAOImpl implements UserDAO {

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<UserVO> getUserList() {
		List<UserVO> vos = sqlSession.selectList("userNS.getUserList");  // sql session factory 명령이 여기 들어감 // => JPA 명령어 // mapper의 id 적어줌
		return vos;
	}

	@Override
	public int setUserDelete(int idx) {
		return sqlSession.delete("userNS.setUserDelete", idx);
	}

	@Override
	public int setUserInputOk(UserVO vo) {
		return sqlSession.insert("userNS.setUserInputOk", vo);
	}

//	@Override
//	public List<UserVO> getUserSearchOk(String keyword) {
//		List<UserVO> vos = sqlSession.selectList("userNS.getUserSearchOk", keyword);
//		return vos;
//	}
	
	@Override
	public List<UserVO> getUserIdSearch(String mid) {
		return sqlSession.selectList("userNS.getUserIdSearch", mid);
	}

	@Override
	public int setUserUpdateOk(UserVO vo) {
		return sqlSession.update("userNS.setUserUpdateOk", vo);
	}
}
