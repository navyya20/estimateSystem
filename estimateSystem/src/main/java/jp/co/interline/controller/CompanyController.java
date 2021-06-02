package jp.co.interline.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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

import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class CompanyController {
	@Autowired
	UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
	
	@RequestMapping(value = "/all/companyList", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String mainPage(HttpSession session) {
		return "accountInform/accountList";
	}
	
}
