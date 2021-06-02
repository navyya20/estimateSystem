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

	public int insertAccount(AccountDTO account) {
		AccountMapper mapper = sqlsession.getMapper(AccountMapper.class);
		int result = mapper.insertAccount(account);
		return result;
	}

	public int updateAccount(AccountDTO account) {
		AccountMapper mapper = sqlsession.getMapper(AccountMapper.class);
		int result = mapper.updateAccount(account);
		return result;
	}

	public AccountDTO getAccount(int accountInformNum) {
		AccountMapper mapper = sqlsession.getMapper(AccountMapper.class);
		AccountDTO account = mapper.getAccount(accountInformNum);
		return account;
	}

	public int deleteAccount(int accountInformNum) {
		AccountMapper mapper = sqlsession.getMapper(AccountMapper.class);
		int result = mapper.deleteAccount(accountInformNum);
		return result;
	}
	
}
