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
	
	
	
	
}
