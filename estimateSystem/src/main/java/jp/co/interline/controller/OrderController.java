package jp.co.interline.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.interline.dto.DocumentTypeDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.orderSystem.orderA.OrderADTO;
import jp.co.interline.service.EstimateService;
import jp.co.interline.service.OrderService;
import jp.co.interline.service.WorkflowService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class OrderController {
	@Autowired
	WorkflowService workflowService;
	@Autowired
	OrderService orderService;
	@Autowired
	EstimateService estimateService;
	

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	/*
	 * option:order by절에 들어갈 스트링
	 * page: pageNavigator를 위한 page수
	 */
	@RequestMapping(value = "/all/orderList", method = RequestMethod.GET)
	public String orderList(HttpSession session, Model model, String flagObj, 
			@RequestParam(value="option", defaultValue="o.updateDate desc")String option, 
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="countPerPage", defaultValue="20") int countPerPage) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("OrderController.orderList,user:{}",user.getUserNum());
		
		ArrayList<EstimateListDTO> orderList = orderService.getOrderList(model, user,option,page,countPerPage);
		
		model.addAttribute("orderList", orderList);
		model.addAttribute("option", option);
		model.addAttribute("flagObj", flagObj);
		model.addAttribute("countPerPage", countPerPage);
		//네비게이션에대한 모델은 서비스에서 넣어준다.
		return "orderSystem/orderList";
	}
	
	@RequestMapping(value = "/all/selectOrder", method = RequestMethod.GET)
	public String selectBill(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.selectBill,user:{}",user.getUserNum());
		ArrayList<DocumentTypeDTO> typeList = orderService.getOrderTypeList();
		model.addAttribute("typeList", typeList);
		return "orderSystem/orderSelect";
	}
	
	public String getWriteDocumentPage(HttpSession session, Model model, String copy, String documentTypeName) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		Gson gson = new Gson();
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		model.addAttribute("copy", copy);
		return "orderSystem/"+documentTypeName+"/write"+documentTypeName.substring(0, 1).toUpperCase()+documentTypeName.substring(1);
	}
	public String getModDocumentPage(HttpSession session, Model model, String documentNum, String documentTypeName) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		//state를 가져옴. documentTypeName에따라 조인되는 DB가 다르니 주의!
		SystemDTO system = new SystemDTO();
		system.setDocumentNum(documentNum);
		system.setDocumentTypeName(documentTypeName);
		//document와 documentMaster에서 기본정보를 가져온다.
		SystemDTO sys = estimateService.getEstimate(system);
		model.addAttribute("state", sys.getState());
		model.addAttribute("userNum", sys.getUserNum());
		model.addAttribute("orderNum", documentNum);
		
		Gson gson = new Gson();
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		return "orderSystem/"+documentTypeName+"/mod"+documentTypeName.substring(0, 1).toUpperCase()+documentTypeName.substring(1);
	}
	
	//bill의 copy기능은 estimate의 copy기능을 공유한다.
	
	//각 서식(billC,billD)에 맞는 ozr 페이지로 연계한다.
	@RequestMapping(value = {"/all/writeOrderA","/all/writeOrderB"}, method = RequestMethod.GET)
	public String writeBillSolution(HttpSession session, Model model, String copy, String documentTypeName) {
		return getWriteDocumentPage(session, model, copy, documentTypeName);
	}
	
	//각 서식(billC,billD)에 맞는 ozr 페이지로 연계한다.
	@RequestMapping(value = {"/all/modOrderA","/all/modOrderB"}, method = RequestMethod.POST)
	public String modBillSolution(HttpSession session, Model model, String documentNum, String documentTypeName) {
		return getModDocumentPage(session, model, documentNum, documentTypeName);
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////   orderA    ////////////////////////////////////////////////////////////////////
	@ResponseBody
	@RequestMapping(value = "/all/insertOrderA", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> insertOrderA(HttpSession session, Model model, String jsonStr) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("OrderController.insertOrderA,user:{}",user.getUserNum());
		return orderService.insertDocument(user, session, model, jsonStr, OrderADTO.class);
	}
	@ResponseBody
	@RequestMapping(value = "/all/updateOrderA", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateOrderA(HttpSession session, Model model, String jsonStr) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("OrderController.updateOrderA,user:{}",user.getUserNum());
		return orderService.updateDocument(user, session, model, jsonStr, OrderADTO.class);
	}
	
}
