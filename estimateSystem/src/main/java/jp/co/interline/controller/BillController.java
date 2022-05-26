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
import jp.co.interline.dto.billSystem.billC.BillCDTO;
import jp.co.interline.dto.billSystem.billD.BillDDTO;
import jp.co.interline.dto.billSystem.billSi.BillSiDTO;
import jp.co.interline.dto.billSystem.billSolution.BillSolutionDTO;
import jp.co.interline.dto.billSystem.monthlyBillTotal.MonthlyBillTotalAjaxDTO;
import jp.co.interline.dto.billSystem.monthlyBillTotal.MonthlyBillTotalAjaxForBillSiDTO;
import jp.co.interline.service.BillService;
import jp.co.interline.service.EstimateService;
import jp.co.interline.service.WorkflowService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BillController {
	@Autowired
	WorkflowService workflowService;
	@Autowired
	BillService billService;
	@Autowired
	EstimateService estimateService;
	

	private static final Logger logger = LoggerFactory.getLogger(BillController.class);
	
	/*
	 * option:order by절에 들어갈 스트링
	 * page: pageNavigator를 위한 page수
	 */
	@RequestMapping(value = "/all/billList", method = RequestMethod.GET)
	public String billList(HttpSession session, Model model, String flagObj, 
			@RequestParam(value="option", defaultValue="b.updateDate desc")String option, 
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="countPerPage", defaultValue="20") int countPerPage) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.esimateList,user:{}",user.getUserNum());
		
		ArrayList<EstimateListDTO> billList = billService.getBillList(model, user,option,page,countPerPage);
		
		model.addAttribute("billList", billList);
		model.addAttribute("option", option);
		model.addAttribute("flagObj", flagObj);
		model.addAttribute("countPerPage", countPerPage);
		//네비게이션에대한 모델은 서비스에서 넣어준다.
		return "billSystem/billList";
	}
	
	@RequestMapping(value = "/all/selectBill", method = RequestMethod.GET)
	public String selectBill(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.selectBill,user:{}",user.getUserNum());
		ArrayList<DocumentTypeDTO> typeList = billService.getBillTypeList();
		model.addAttribute("typeList", typeList);
		return "billSystem/billSelect";
	}
	
	public String getWriteDocumentPage(HttpSession session, Model model, String copy, String documentTypeName) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		Gson gson = new Gson();
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		model.addAttribute("copy", copy);
		return "billSystem/"+documentTypeName+"/write"+documentTypeName.substring(0, 1).toUpperCase()+documentTypeName.substring(1);
	}
	public String getModDocumentPage(HttpSession session, Model model, String documentNum, String documentTypeName) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		//state를 가져옴. documentTypeName에따라 조인되는 DB가 다르니 주의!
		SystemDTO system = new SystemDTO();
		system.setDocumentNum(documentNum);
		system.setDocumentTypeName(documentTypeName);
		SystemDTO sys = estimateService.getEstimate(system);
		model.addAttribute("state", sys.getState());
		model.addAttribute("userNum", sys.getUserNum());
		model.addAttribute("billNum", documentNum);
		
		Gson gson = new Gson();
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		return "billSystem/"+documentTypeName+"/mod"+documentTypeName.substring(0, 1).toUpperCase()+documentTypeName.substring(1);
	}
	
	
	@RequestMapping(value = "/all/monthlyBillTotal", method = RequestMethod.GET)
	public String monthlyBillTotal(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.monthlyBillTotal,user:{}",user.getUserNum());
		ArrayList<DocumentTypeDTO> typeList = billService.getBillTypeList();
		model.addAttribute("typeList", typeList);
		return "billSystem/monthlyBillTotal/monthlyBillTotal";
	}
	
	@RequestMapping(value = "/all/monthlyBillTotal/monthlyBillTotalAjax", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String monthlyBillTotalAjax(HttpSession session, Model model, String billType, int year, int month) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.monthlyBillTotal.monthlyBillTotalAjax,user:{}",user.getUserNum());
		if("billSi".equals(billType)) {
			ArrayList<MonthlyBillTotalAjaxForBillSiDTO> billList = billService.getMonthlyBillSiTotalList(year, month, user.getUserNum(), user.getAuth());
			float totalPrice = 0;
			float totalExpenses = 0;
			for (MonthlyBillTotalAjaxForBillSiDTO monthlyBillTotalAjaxForBillSiDTO : billList) {
				totalPrice += monthlyBillTotalAjaxForBillSiDTO.getPrice();
				totalExpenses += monthlyBillTotalAjaxForBillSiDTO.getSubtotal();
			}
			model.addAttribute("billList", billList);
			model.addAttribute("totalPrice", totalPrice);
			model.addAttribute("totalExpenses", totalExpenses);
			return "billSystem/monthlyBillTotal/monthlyBillTotalAjaxForBillSi";
		}
		ArrayList<MonthlyBillTotalAjaxDTO> monthlyBillTotalList = billService.getMonthlyBillTotalList(billType, year, month, user.getUserNum(), user.getAuth());
		float total = 0;
		for (MonthlyBillTotalAjaxDTO monthlyBillTotalAjaxDTO : monthlyBillTotalList) {
			total += monthlyBillTotalAjaxDTO.getSumWithTax();
		}
		model.addAttribute("billList", monthlyBillTotalList);
		model.addAttribute("total", total);
		return "billSystem/monthlyBillTotal/monthlyBillTotalAjax";
	}
	
	
	
	
	
	
	//bill의 copy기능은 estimate의 copy기능을 공유한다.
	
	//각 서식(billC,billD)에 맞는 ozr 페이지로 연계한다.
	@RequestMapping(value = {"/all/writeBillSolution","/all/writeBillSi","/all/writeBillC","/all/writeBillD"}, method = RequestMethod.GET)
	public String writeBillSolution(HttpSession session, Model model, String copy, String documentTypeName) {
		return getWriteDocumentPage(session, model, copy, documentTypeName);
	}
	
	//각 서식(billC,billD)에 맞는 ozr 페이지로 연계한다.
	@RequestMapping(value = {"/all/modBillSolution","/all/modBillSi","/all/modBillC","/all/modBillD"}, method = RequestMethod.POST)
	public String modBillSolution(HttpSession session, Model model, String documentNum, String documentTypeName) {
		return getModDocumentPage(session, model, documentNum, documentTypeName);
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////   billSolution    //////////////////////////////////////////////////////////////
	@ResponseBody
	@RequestMapping(value = "/all/insertBillSolution", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> insertBillSolution(HttpSession session, Model model, String jsonStr) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.insertBillSolution,user:{}",user.getUserNum());
		return billService.insertDocument(user, session, model, jsonStr, BillSolutionDTO.class);
	}
	
	@ResponseBody
	@RequestMapping(value = "/all/updateBillSolution", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateBillSolution(HttpSession session, Model model, String jsonStr) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.updateBillSolution,user:{}",user.getUserNum());
		return billService.updateDocument(user, session, model, jsonStr, BillSolutionDTO.class);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////   billSi    ////////////////////////////////////////////////////////////////////
	@ResponseBody
	@RequestMapping(value = "/all/insertBillSi", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> insertBillSi(HttpSession session, Model model, String jsonStr) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.insertBillSi,user:{}",user.getUserNum());
		return billService.insertDocument(user, session, model, jsonStr, BillSiDTO.class);
	}
	@ResponseBody
	@RequestMapping(value = "/all/updateBillSi", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateBillSi(HttpSession session, Model model, String jsonStr) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.updateBillSi,user:{}",user.getUserNum());
		return billService.updateDocument(user, session, model, jsonStr, BillSiDTO.class);
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////   billC    /////////////////////////////////////////////////////////////////////	
	@ResponseBody
	@RequestMapping(value = "/all/insertBillC", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> insertBillC(HttpSession session, Model model, String jsonStr) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.insertBillC,user:{}",user.getUserNum());
		return billService.insertDocument(user, session,model,jsonStr,BillCDTO.class);
	}
	@ResponseBody
	@RequestMapping(value = "/all/updateBillC", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateBillC(HttpSession session, Model model, String jsonStr) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.updateBillC,user:{}",user.getUserNum());
		return billService.updateDocument(user, session, model, jsonStr, BillCDTO.class);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////   billD    /////////////////////////////////////////////////////////////////////
	@ResponseBody
	@RequestMapping(value = "/all/insertBillD", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> insertBillD(HttpSession session, Model model, String jsonStr) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.insertBillD,user:{}",user.getUserNum());
		return billService.insertDocument(user, session,model,jsonStr,BillDDTO.class);
	}
	@ResponseBody
	@RequestMapping(value = "/all/updateBillD", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateBillD(HttpSession session, Model model, String jsonStr) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.updateBillD,user:{}",user.getUserNum());
		return billService.updateDocument(user, session, model, jsonStr, BillDDTO.class);
	}	
	
}
