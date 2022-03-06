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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import jp.co.interline.dto.CompanyDTO;
import jp.co.interline.dto.DocumentMasterDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.AccountDTO;
import jp.co.interline.dto.BillCDTO;
import jp.co.interline.dto.BillCItemsRecieveDTO;
import jp.co.interline.dto.BillDDTO;
import jp.co.interline.dto.BillSheet1DTO;
import jp.co.interline.dto.BillSheet1ItemsRecieveDTO;
import jp.co.interline.dto.BillSiDTO;
import jp.co.interline.dto.BillSiItemsRecieveDTO;
import jp.co.interline.dto.BillSolutionDTO;
import jp.co.interline.dto.BillSolutionItemsRecieveDTO;
import jp.co.interline.dto.EstimateSheet1DTO;
import jp.co.interline.dto.EstimateSheet1ItemsRecieveDTO;
import jp.co.interline.dto.FileNamesDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.WorkflowInformDTO;
import jp.co.interline.service.AccountService;
import jp.co.interline.service.BillService;
import jp.co.interline.service.CompanyService;
import jp.co.interline.service.EstimateService;
import jp.co.interline.service.WorkflowService;
import jp.co.interline.service.jacksonUtility;

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
		
		return "billSystem/billSelect";
	}
	
	//bill의 copy기능은 estimate의 copy기능을 공유한다.
	


	
	
	
	
	
/////////////////////////////billSolution///////////////////////////////////////////////////////////////////
//write,insert,mod,update	
	@RequestMapping(value = "/all/writeBillSolution", method = RequestMethod.GET)
	public String writeBillSolution(HttpSession session, Model model, String estimateNum, String copy) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		Gson gson = new Gson();
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		model.addAttribute("estimateNum", estimateNum);
		model.addAttribute("copy", copy);
		return "billSystem/billSolution/writeBillSolution";
	}
	
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@ResponseBody
	@RequestMapping(value = "/all/insertBillSolution", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> insertBillSolution(HttpSession session, Model model, BillSolutionDTO billSolution, BillSolutionItemsRecieveDTO billSolutionItemsReciever) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.insertBillSolution,user:{}",user.getUserNum());
		//채번
		String documentNum = billService.getDocoumentNum();
		//기본정보등록
		billSolution.setDocumentNum(documentNum);
		billSolution.setUpdater(user.getUserNum());
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		int result1 = billService.insertBillSolution(billSolution);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "請求基本情報格納エラー");
			return result;
		}
		//아이템등록
		billSolutionItemsReciever.setDocumentNum(documentNum);
		int result2 = billService.insertBillSolutionItems(billSolutionItemsReciever);
		if (result2 ==0) {
			result.put("errorFlag", "1");
			result.put("error", "請求ITEM情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", documentNum);
		return result;
	}
	
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@RequestMapping(value = "/all/modBillSolution", method = RequestMethod.POST)
	public String modBillSolution(HttpSession session, Model model, String documentNum, String documentTypeName) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		
		//state를 가져옴.
		//documentTypeName에따라 조인되는 DB가 다르니 주의!
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
		return "billSystem/billSolution/modBillSolution";
	}
	
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@ResponseBody
	@RequestMapping(value = "/all/updateBillSolution", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateBillSolution(HttpSession session, Model model, BillSolutionDTO billSolution, BillSolutionItemsRecieveDTO billSolutionItemsReciever) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		
		HashMap<String, String> result = new HashMap<String, String>();
		//기본정보등록
		int result1 = billService.updateBillSolution(billSolution);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "請求基本情報格納エラー");
			return result;
		}
		//아이템등록
		billSolutionItemsReciever.setDocumentNum(billSolution.getDocumentNum());
		int result2 = billService.updateBillSolutionItems(billSolutionItemsReciever);
		if (result2 ==0) {
			result.put("errorFlag", "1");
			result.put("error", "請求ITEM情報格納エラー");
			return result;
		}
		
		result.put("errorFlag", "0");
		result.put("documentNum", billSolution.getDocumentNum());
		return result;
	}
	
	
	
	
/////////////////////////////billSi//////////////////////////////////////////////////
//write,insert,mod,update	
	
	@RequestMapping(value = "/all/writeBillSi", method = RequestMethod.GET)
	public String writeBillSi(HttpSession session, Model model, String estimateNum, String copy) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		Gson gson = new Gson();
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		model.addAttribute("estimateNum", estimateNum);
		model.addAttribute("copy", copy);
		return "billSystem/billSi/writeBillSi";
	}
	
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@ResponseBody
	@RequestMapping(value = "/all/insertBillSi", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> insertBillSi(HttpSession session, Model model, BillSiDTO billSi, BillSiItemsRecieveDTO billSiItemsReciever) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.insertBillSi,user:{}",user.getUserNum());
		//채번
		String documentNum = billService.getDocoumentNum();
		//기본정보등록
		billSi.setDocumentNum(documentNum);
		billSi.setUpdater(user.getUserNum());
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		int result1 = billService.insertBillSi(billSi);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "請求基本情報格納エラー");
			return result;
		}
		//아이템등록
		billSiItemsReciever.setDocumentNum(documentNum);
		int result2 = billService.insertBillSiItems(billSiItemsReciever);
		if (result2 ==0) {
			result.put("errorFlag", "1");
			result.put("error", "請求ITEM情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", documentNum);
		return result;
	}
	
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@RequestMapping(value = "/all/modBillSi", method = RequestMethod.POST)
	public String modBillSi(HttpSession session, Model model, String documentNum, String documentTypeName) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		
		//state를 가져옴.
		//documentTypeName에따라 조인되는 DB가 다르니 주의!
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
		return "billSystem/billSi/modBillSi";
	}

	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@ResponseBody
	@RequestMapping(value = "/all/updateBillSi", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateBillSi(HttpSession session, Model model, BillSiDTO billSi, BillSiItemsRecieveDTO billSiItemsReciever) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		
		HashMap<String, String> result = new HashMap<String, String>();
		//기본정보등록
		int result1 = billService.updateBillSi(billSi);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "請求基本情報格納エラー");
			return result;
		}
		//아이템등록
		billSiItemsReciever.setDocumentNum(billSi.getDocumentNum());
		int result2 = billService.updateBillSiItems(billSiItemsReciever);
		if (result2 ==0) {
			result.put("errorFlag", "1");
			result.put("error", "請求ITEM情報格納エラー");
			return result;
		}
		
		result.put("errorFlag", "0");
		result.put("documentNum", billSi.getDocumentNum());
		return result;
	}

	
/////////////////////////////billC//////////////////////////////////////////////////
	//write,insert,mod,update	
	
	@RequestMapping(value = "/all/writeBillC", method = RequestMethod.GET)
	public String writeBillC(HttpSession session, Model model, String estimateNum, String copy) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		Gson gson = new Gson();
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		model.addAttribute("estimateNum", estimateNum);
		model.addAttribute("copy", copy);
		return "billSystem/billC/writeBillC";
	}
	
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@ResponseBody
	@RequestMapping(value = "/all/insertBillC", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> insertBillC(HttpSession session, Model model, BillCDTO billC, BillCItemsRecieveDTO billCItemsReciever) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.insertBillC,user:{}",user.getUserNum());
		//채번
		String documentNum = billService.getDocoumentNum();
		//기본정보등록
		billC.setDocumentNum(documentNum);
		billC.setUpdater(user.getUserNum());
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		int result1 = billService.insertBillC(billC);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "請求基本情報格納エラー");
			return result;
		}
		//아이템등록
		billCItemsReciever.setDocumentNum(documentNum);
		int result2 = billService.insertBillCItems(billCItemsReciever);
		if (result2 ==0) {
			result.put("errorFlag", "1");
			result.put("error", "請求ITEM情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", documentNum);
		return result;
	}
	
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@RequestMapping(value = "/all/modBillC", method = RequestMethod.POST)
	public String modBillC(HttpSession session, Model model, String documentNum, String documentTypeName) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		
		//state를 가져옴.
		//documentTypeName에따라 조인되는 DB가 다르니 주의!
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
		return "billSystem/billC/modBillC";
	}
	
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@ResponseBody
	@RequestMapping(value = "/all/updateBillC", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateBillC(HttpSession session, Model model, BillCDTO billC, BillCItemsRecieveDTO billCItemsReciever) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		
		HashMap<String, String> result = new HashMap<String, String>();
		//기본정보등록
		int result1 = billService.updateBillC(billC);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "請求基本情報格納エラー");
			return result;
		}
		//아이템등록
		billCItemsReciever.setDocumentNum(billC.getDocumentNum());
		int result2 = billService.updateBillCItems(billCItemsReciever);
		if (result2 ==0) {
			result.put("errorFlag", "1");
			result.put("error", "請求ITEM情報格納エラー");
			return result;
		}
		
		result.put("errorFlag", "0");
		result.put("documentNum", billC.getDocumentNum());
		return result;
	}
	
	
	
/////////////////////////////billD//////////////////////////////////////////////////
//write,insert,mod,update	
	@RequestMapping(value = "/all/writeBillD", method = RequestMethod.GET)
	public String writeBillD(HttpSession session, Model model, String estimateNum, String copy) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		Gson gson = new Gson();
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		model.addAttribute("estimateNum", estimateNum);
		model.addAttribute("copy", copy);
		return "billSystem/billD/writeBillD";
	}
	
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@ResponseBody
	@RequestMapping(value = "/all/insertBillD", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> insertBillD(HttpSession session, Model model, String jsonStr) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillDontroller.insertBillD,user:{}",user.getUserNum());
		BillDDTO billDDTO = null;
		try {
			billDDTO = jacksonUtility.readValue(jsonStr, BillDDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//채번
		String documentNum = billService.getDocoumentNum();
		//기본정보등록
		billDDTO.setDocumentNum(documentNum);
		billDDTO.setUpdater(user.getUserNum());
		HashMap<String, String> result = new HashMap<String, String>();
		int result1 = billService.insertBillD(billDDTO);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "請求情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", documentNum);
		return result;
	}
	
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@RequestMapping(value = "/all/modBillD", method = RequestMethod.POST)
	public String modBillD(HttpSession session, Model model, String documentNum, String documentTypeName) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		
		//state를 가져옴.
		//documentTypeName에따라 조인되는 DB가 다르니 주의!
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
		return "billSystem/billD/modBillD";
	}
	
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@ResponseBody
	@RequestMapping(value = "/all/updateBillD", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateBillD(HttpSession session, Model model, String jsonStr) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		BillDDTO billDDTO = null;
		try {
			billDDTO = jacksonUtility.readValue(jsonStr, BillDDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HashMap<String, String> result = new HashMap<String, String>();
		//기본정보등록
		int result1 = billService.updateBillD(billDDTO);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "請求情報格納エラー");
			return result;
		}
		
		result.put("errorFlag", "0");
		result.put("documentNum", billDDTO.getDocumentNum());
		return result;
	}	
	
}
