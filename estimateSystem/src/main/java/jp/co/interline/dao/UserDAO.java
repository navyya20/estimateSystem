package jp.co.interline.dao;


import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.interline.dto.UserInformDTO;




@Repository
public class UserDAO implements UserMapper {
	
	@Autowired
	SqlSession sqlsession;

	
	@Override
	public UserInformDTO getUserInformByIdPw(UserInformDTO userInform) {
		UserMapper mapper = sqlsession.getMapper(UserMapper.class);
		UserInformDTO user = mapper.getUserInformByIdPw(userInform);
		
		return user;
	}

	@Override
	public ArrayList<UserInformDTO> getUserList(UserInformDTO user) {
		UserMapper mapper = sqlsession.getMapper(UserMapper.class);
		ArrayList<UserInformDTO> userList = mapper.getUserList(user);
		return userList;
	}
	
	@Override
	public int insertUser(UserInformDTO userInform) {
		UserMapper mapper = sqlsession.getMapper(UserMapper.class);
		int result = mapper.insertUser(userInform);
		return result;
	}
	
	@Override
	public UserInformDTO getUserInformByUserNum(int userNum) {
		UserMapper mapper = sqlsession.getMapper(UserMapper.class);
		UserInformDTO userInform = mapper.getUserInformByUserNum(userNum);
		return userInform;
	}

	@Override
	public int updateUser(UserInformDTO userInform) {
		UserMapper mapper = sqlsession.getMapper(UserMapper.class);
		int result = mapper.updateUser(userInform);
		return result;
	}

	public int deleteUser(int userNum) {
		UserMapper mapper = sqlsession.getMapper(UserMapper.class);
		int result = mapper.deleteUser(userNum);
		return result;
	}
	
	
	
}
