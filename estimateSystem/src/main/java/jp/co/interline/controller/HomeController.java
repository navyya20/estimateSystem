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
public class HomeController {
	@Autowired
	UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		return "login";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String longin(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		return "login";
	}
	
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> Login(HttpSession session,String userId, String password, String checkDevice) {
		logger.debug("login id:{}, pw:{}", userId,password);
		UserInformDTO userIdPassword = new UserInformDTO();
		userIdPassword.setUserId(userId);
		userIdPassword.setPassword(password);
		
		UserInformDTO user = userService.getUserInformByIdPw(userIdPassword);
		HashMap<String,String> result = new HashMap<>();
		
		if(user != null){
			
			session.setAttribute("loginId", user.getUserId());
			session.setAttribute("userInform", user);
			JSONObject jsonObject = new JSONObject(user);
			session.setAttribute("userInformJsonString", jsonObject.toString());
			session.setAttribute("device",checkDevice);
			
			switch (user.getAuth()) {
			case "u":
				result.put("url", "all/dashboard");
				break;
			case "a":
				result.put("url", "all/dashboard");
				break;
			case "sa":
				result.put("url", "all/dashboard");
				break;
			default:
				result.put("error","権限に当たるURLの読み込みに失敗しました。");
				break;
			}
		}
		
		if(user == null) {
			result.put("error","IDまたはPWが間違っています。");
		}else if(!user.getPassword().equals(password)){
			result.put("error","PASSWORDが合致しません。");
		}
		//여기에 로그인데이트 업데이트 코드
		return result;
	}
	
	
	@RequestMapping(value = "/all/dashboard", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String mainPage(HttpSession session) {
		return "dashboard";
	}
	
}