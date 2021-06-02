package jp.co.interline.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		ArrayList<AccountDTO> accountList = accountService.getAccountList();
		model.addAttribute("accountList", accountList);
		return "accountInform/accountList";
	}
	
}
