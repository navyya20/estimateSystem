package jp.co.interline.dao;

import java.util.ArrayList;

import jp.co.interline.dto.AccountDTO;


public interface AccountMapper {

	ArrayList<AccountDTO> getAccountList();

	int insertAccount(AccountDTO account);

	int updateAccount(AccountDTO account);

	AccountDTO getAccount(int accountInformNum);

	int deleteAccount(int accountInformNum);
	
}
