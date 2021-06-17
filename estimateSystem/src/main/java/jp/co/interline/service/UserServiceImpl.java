package jp.co.interline.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.interline.dao.UserDAO;
import jp.co.interline.dto.UserInformDTO;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDAO userDao;
	
	
	@Override
	public UserInformDTO getUserInformByIdPw(UserInformDTO userInform){
		return userDao.getUserInformByIdPw(userInform);
	}


	@Override
	public ArrayList<UserInformDTO> getUserList(UserInformDTO user) {
		
		return userDao.getUserList(user);
	}

	@Override
	public ArrayList<UserInformDTO> getUserListOrderd(String where) {
		return userDao.getUserListOrderd(where);
	}

	@Override
	public int insertUser(UserInformDTO userInform) {
		int result = userDao.insertUser(userInform);
		return result;
	}


	@Override
	public UserInformDTO getUserInformByUserNum(int userNum) {
		UserInformDTO userInform = userDao.getUserInformByUserNum(userNum);
		return userInform;
	}


	@Override
	public int updateUser(UserInformDTO userInform) {
		int result = userDao.updateUser(userInform);
		return result;
	}


	@Override
	public int deleteUser(int userNum) {
		int result = userDao.deleteUser(userNum);
		return result;
	}


	@Override
	public ArrayList<UserInformDTO> getUserListOrderdOnlyAdmin(String where) {
		return userDao.getUserListOrderdOnlyAdmin(where);
	}


	


	
	
	
}
