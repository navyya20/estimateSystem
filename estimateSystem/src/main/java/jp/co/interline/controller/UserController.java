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

import jp.co.interline.dto.AuthDTO;
import jp.co.interline.dto.DepartmentDTO;
import jp.co.interline.dto.PositionDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.service.BasicService;
import jp.co.interline.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	BasicService basicService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**
	 * 유저 정보 페이지
	 */
	@RequestMapping(value = "/all/userList", method = RequestMethod.GET)
	public String userList(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		ArrayList<UserInformDTO> userList = userService.getUserList(user);
		model.addAttribute("userList", userList);
		return "userInform/userList";
	}
	
	
	/**
	 * 유저 등록 페이지
	 */
	@RequestMapping(value = "/all/userReg", method = RequestMethod.GET)
	public String userReg(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		ArrayList<DepartmentDTO> departments = basicService.getDepartments();
		ArrayList<PositionDTO> positions = basicService.getPositions();
		ArrayList<AuthDTO> auths = basicService.getAuths();
		
		model.addAttribute("departments", departments);
		model.addAttribute("positions", positions);
		model.addAttribute("auths", auths);
		
		return "userInform/userReg";
	}
	
	@RequestMapping(value = "/all/insertUser", method = RequestMethod.POST)
	public String insertUser(HttpSession session, Model model, UserInformDTO userInform) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		userInform.setUpdater(user.getUserNum());
		logger.debug("{}がuserInsert実行", user.getUserNum());
		logger.debug("userInform : {}", userInform);
		int result = userService.insertUser(userInform);
		if (result != 1) {
			System.out.println("failed for inserting userInform");
		}
		System.out.println("userInform inserted");
		return "redirect:/all/userList";
	}
	
	@RequestMapping(value = "/all/userMod", method = RequestMethod.POST)
	public String userMod(HttpSession session, Model model, int userNum) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("{}がuserMod実行", user.getUserNum());
		UserInformDTO userInform = userService.getUserInformByUserNum(userNum);
		logger.debug("userInform : {}", userInform);
		ArrayList<DepartmentDTO> departments = basicService.getDepartments();
		ArrayList<PositionDTO> positions = basicService.getPositions();
		ArrayList<AuthDTO> auths = basicService.getAuths();
		
		model.addAttribute("departments", departments);
		model.addAttribute("positions", positions);
		model.addAttribute("auths", auths);
		model.addAttribute("user", userInform);
		return "userInform/userMod";
	}
	
	@RequestMapping(value = "/all/updateUser", method = RequestMethod.POST)
	public String updateUser(HttpSession session, Model model, UserInformDTO userInform) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		userInform.setUpdater(user.getUserNum());
		logger.debug("{}がupdateUser実行", user.getUserNum());
		logger.debug("userInform : {}", userInform);
		int result = userService.updateUser(userInform);
		if (result != 1) {
			System.out.println("failed for inserting userInform");
		}
		if(userInform.getUserNum()==user.getUserNum()) {
			UserInformDTO updatedUser = userService.getUserInformByUserNum(user.getUserNum());
			session.setAttribute("userInform", updatedUser);
		}
		logger.info("userInform updated");
		return "redirect:/all/userList";
	}
	
	@RequestMapping(value = "/all/deleteUser", method = RequestMethod.POST)
	public String deleteUser(HttpSession session, Model model, int userNum) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("{}がdeleteUser実行", user.getUserNum());
		logger.debug("userNum : {}", userNum);
		int result = userService.deleteUser(userNum);
		if (result != 1) {
			logger.info("failed for deleting userInform");
		}
		logger.info("userInform deleted");
		return "redirect:/all/userList";
	}
	
}
