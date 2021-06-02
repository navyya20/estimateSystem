package jp.co.interline.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.interline.dto.AccountDTO;
import jp.co.interline.dto.AuthDTO;
import jp.co.interline.dto.DepartmentDTO;
import jp.co.interline.dto.PositionDTO;

@Repository
public class AccountDAO implements AccountMapper {
	@Autowired
	SqlSession sqlsession;

	public ArrayList<AccountDTO> getAccountList() {
		AccountMapper mapper = sqlsession.getMapper(AccountMapper.class);
		ArrayList<AccountDTO> accountList = mapper.getAccountList();
		return accountList;
	}
	
}
