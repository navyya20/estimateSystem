package jp.co.interline.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;

import jp.co.interline.dto.UserInformDTO;



public interface UserMapper {
	

	public UserInformDTO getUserInformByIdPw(UserInformDTO userInform);

	public ArrayList<UserInformDTO> getUserList(UserInformDTO user);

	public int insertUser(UserInformDTO userInform);

	public UserInformDTO getUserInformByUserNum(int userNum);

	int updateUser(UserInformDTO userInform);

	public int deleteUser(int userNum);



}
