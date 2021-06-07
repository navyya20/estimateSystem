package jp.co.interline.controller;

import java.io.File;
import java.lang.reflect.Array;
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
import org.springframework.web.multipart.MultipartFile;

import jp.co.interline.dto.CompanyDTO;
import jp.co.interline.dto.FileNamesDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.WorkflowInformDTO;
import jp.co.interline.service.CompanyService;
import jp.co.interline.service.FileService;
import jp.co.interline.service.UserService;
import jp.co.interline.service.WorkflowService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class WorkflowController {
	@Autowired
	WorkflowService workflowService;
	@Autowired
	UserService userServive;
	
	private static final Logger logger = LoggerFactory.getLogger(WorkflowController.class);
	
	@RequestMapping(value = "/all/workflowList", method = RequestMethod.GET)
	public String workflowList(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		
		String where = "order by department asc, position asc";
		ArrayList<UserInformDTO> userList =  userServive.getUserListOrderd(where);
		
		WorkflowInformDTO estimateSystem = workflowService.getWorkflowInformWithNameBySystemNum(1);
		WorkflowInformDTO billSystem = workflowService.getWorkflowInformWithNameBySystemNum(2);
		
		model.addAttribute("userList", userList);
		model.addAttribute("estimateSystem", estimateSystem);
		model.addAttribute("billSystem", billSystem);
		
		return "workflowInform/workflowList";
	}
	
	@ResponseBody
	@RequestMapping(value = "/all/updateWorkflowInform", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateWorkflowInform(HttpSession session, Model model, int userNum, int systemNum, int order) {
		
		int r1 = workflowService.updateWorkflowInform(userNum, systemNum, order);
		int r2= workflowService.updateTarget(systemNum);
		
		HashMap<String,String> result = new HashMap<>();
		if(r1!=1||r2!=1) {
			result.put("error","処理エラー");
		}
		return result;
	}
}
