package jp.co.interline.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.interline.dto.AccountDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.service.AccountService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class AccountController {
	@Autowired
	AccountService accountService;
	
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@RequestMapping(value = "/all/accountList", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String accountList(HttpSession session, Model model) {
		ArrayList<AccountDTO> accountList = accountService.getAccountList();
		model.addAttribute("accountList", accountList);
		return "accountInform/accountList";
	}
	
	@RequestMapping(value = "/all/accountReg", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String accountReg(HttpSession session, Model model) {
		return "accountInform/accountReg";
	}
	
	@RequestMapping(value = "/all/insertAccount", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String insertAccount(HttpSession session, Model model, AccountDTO account) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		account.setUpdater(user.getUserNum());
		logger.debug("{}がinsertAccount実行", user.getUserNum());
		logger.debug("account : {}", account);
		int result = accountService.insertAccount(account);
		if (result != 1) {
			logger.info("failed for inserting Account");
		}else {
			logger.info("Account inserted");			
		}
		return "redirect:/all/accountList";
	}
	
	@RequestMapping(value = "/all/accountMod", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String accountMod(HttpSession session, Model model, int accountInformNum) {
		AccountDTO account = accountService.getAccount(accountInformNum);
		model.addAttribute("account", account);
		return "accountInform/accountMod";
	}
	
	@RequestMapping(value = "/all/updateAccount", method = RequestMethod.POST)
	public String updateAccount(HttpSession session, Model model, AccountDTO account) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		account.setUpdater(user.getUserNum());
		logger.debug("{}がupdateAccount実行", user.getUserNum());
		logger.debug("accountInform : {}", account);
		int result = accountService.updateAccount(account);
		if (result != 1) {
			System.out.println("failed for updating Account");
		}else {
			logger.info("Account updated");
		}
		return "redirect:/all/accountList";
	}
	
	@RequestMapping(value = "/all/deleteAccount", method = RequestMethod.POST)
	public String deleteAccount(HttpSession session, Model model, int accountInformNum) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("{}がdeleteAccount実行", user.getUserNum());
		logger.debug("accountInformNum : {}", accountInformNum);
		int result = accountService.deleteAccount(accountInformNum);
		if (result != 1) {
			logger.info("failed for deleting accountInform");
		}else {
			logger.info("accountInform deleted");
		}
		return "redirect:/all/accountList";
	}
	
}
