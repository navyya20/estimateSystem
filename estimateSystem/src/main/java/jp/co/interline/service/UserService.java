package jp.co.interline.service;


import java.util.ArrayList;

import jp.co.interline.dto.UserInformDTO;


public interface UserService {
	
	public UserInformDTO getUserInformByIdPw(UserInformDTO userInform);

	public ArrayList<UserInformDTO> getUserList(UserInformDTO user);

	public int insertUser(UserInformDTO userInform);

	public UserInformDTO getUserInformByUserNum(int userNum);

	public int updateUser(UserInformDTO userInform);

	public int deleteUser(int userNum);

	public ArrayList<UserInformDTO> getUserListOrderd(String where);
}
