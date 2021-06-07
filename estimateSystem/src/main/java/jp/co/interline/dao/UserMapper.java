package jp.co.interline.dao;

import java.util.ArrayList;

import jp.co.interline.dto.UserInformDTO;



public interface UserMapper {
	

	public UserInformDTO getUserInformByIdPw(UserInformDTO userInform);

	public ArrayList<UserInformDTO> getUserList(UserInformDTO user);

	public int insertUser(UserInformDTO userInform);

	public UserInformDTO getUserInformByUserNum(int userNum);

	int updateUser(UserInformDTO userInform);

	public int deleteUser(int userNum);

	public ArrayList<UserInformDTO> getUserListOrderd(String condition);





}
