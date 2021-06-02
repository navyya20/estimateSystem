package jp.co.interline.dao;

import java.util.ArrayList;

import jp.co.interline.dto.AccountDTO;


public interface AccountMapper {

	ArrayList<AccountDTO> getAccountList();
	
}
