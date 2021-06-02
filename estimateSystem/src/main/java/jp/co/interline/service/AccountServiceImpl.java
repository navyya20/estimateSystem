package jp.co.interline.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.interline.dao.AccountDAO;
import jp.co.interline.dto.AccountDTO;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	AccountDAO accountDao;

	@Override
	public ArrayList<AccountDTO> getAccountList() {
		ArrayList<AccountDTO> accountList = accountDao.getAccountList();
		return accountList;
	}

	@Override
	public int insertAccount(AccountDTO account) {
		int result = accountDao.insertAccount(account);
		return result;
	}

	@Override
	public int updateAccount(AccountDTO account) {
		int result = accountDao.updateAccount(account);
		return result;
	}

	@Override
	public AccountDTO getAccount(int accountInformNum) {
		AccountDTO account = accountDao.getAccount(accountInformNum);
		return account;
	}

	@Override
	public int deleteAccount(int accountInformNum) {
		int result = accountDao.deleteAccount(accountInformNum);
		return result;
	}
	
	
	
	
}
