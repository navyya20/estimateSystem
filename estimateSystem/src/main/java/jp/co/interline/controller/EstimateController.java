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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import jp.co.interline.dto.AccountDTO;
import jp.co.interline.dto.BillSheet1DTO;
import jp.co.interline.dto.CompanyDTO;
import jp.co.interline.dto.EstimateLanguageDTO;
import jp.co.interline.dto.EstimateLanguageItemsRecieveDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.EstimateSheet1DTO;
import jp.co.interline.dto.EstimateSheet1ItemsRecieveDTO;
import jp.co.interline.dto.EstimateSiDTO;
import jp.co.interline.dto.EstimateSiItemsRecieveDTO;
import jp.co.interline.dto.EstimateSolutionDTO;
import jp.co.interline.dto.EstimateSolutionItemsRecieveDTO;
import jp.co.interline.dto.FileNamesDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.WorkflowInformDTO;
import jp.co.interline.service.AccountService;
import jp.co.interline.service.CompanyService;
import jp.co.interline.service.EstimateService;
import jp.co.interline.service.WorkflowService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class EstimateController {
	@Autowired
	WorkflowService workflowService;
	@Autowired
	EstimateService estimateService;
	@Autowired
	CompanyService companyService;
	@Autowired
	AccountService accountService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(EstimateController.class);
	
	
	/*
	 * option:order by절에 들어갈 스트링
	 * page: pageNavigator를 위한 page수
	 */
	@RequestMapping(value = "/all/estimateList", method = RequestMethod.GET)
	public String estimateList(HttpSession session, Model model, @RequestParam(value="option", defaultValue="e.updateDate desc")String option, @RequestParam(value="page", defaultValue="1") int page) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("EstimateController.esimateList,user:{}",user.getUserNum());
		
		ArrayList<EstimateListDTO> estimateList = estimateService.getEstimateList(model, user,option,page);
		
		model.addAttribute("estimateList", estimateList);
		model.addAttribute("option", option);
		//네비게이션에대한 모델은 서비스에서 넣어준다.
		return "estimateSystem/estimateList";
	}
	
	
	@RequestMapping(value = "/all/selectEstimate", method = RequestMethod.GET)
	public String selectEstimate(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("EstimateController.selectEstimate,user:{}",user.getUserNum());
		
		return "estimateSystem/estimateSelect";
	}
	
	
	@RequestMapping(value = "/all/readEstimate", method = RequestMethod.POST)
	public String readEstimate(HttpSession session, Model model, String documentNum, String documentTypeName, String approveMode) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("EstimateController.readEstimateSheet1,user:{}",user.getUserNum());
		//state를 가져옴.
		//documentTypeName에따라 조인되는 DB가 다르니 주의!
		SystemDTO system = new SystemDTO();
		system.setDocumentNum(documentNum);
		system.setDocumentTypeName(documentTypeName);
		SystemDTO sys = estimateService.getEstimate(system);
		model.addAttribute("state", sys.getState());
		model.addAttribute("userNum", sys.getUserNum());
		model.addAttribute("systemName", sys.getSystemName());
		model.addAttribute("estimateNum", documentNum);
		model.addAttribute("documentTypeName", documentTypeName);
		model.addAttribute("approveMode", approveMode);
		return "estimateSystem/readEstimate";
	}
	
	
	/*
	 * 지울 다큐먼트 리스트를 가져온다.
	 * estiamteMaster에서 딸린 청구서가 있는지 확인한다.
	 * 있으면 documentMaster에서 청구서 먼져 지우고 본 문서를 지운다. 
	 */
	@ResponseBody
	@RequestMapping(value = "/all/deleteSheets", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateEstimateSheet1(HttpSession session, Model model, String[] documentArr) {
		for (int i = 0; i < documentArr.length; i++) {
			System.out.println(documentArr[i]);
			String documentNum = documentArr[i];
			estimateService.deleteSheet(documentNum);
		}
		return null;
	}
	
	
	@RequestMapping(value = "/all/test", method = RequestMethod.GET)
	public String test(HttpSession session, Model model, String documentNum) {
		estimateService.test();
		return "";
	}
	
	
/////////////////////////////////////estimateSheet1///////////////////////////////////////////////////
//write,write copy,insert,mod,update,
	@RequestMapping(value = "/all/writeEstimateSheet1", method = RequestMethod.GET)
	public String writeEstimateSheet1(HttpSession session, Model model, String copy) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		ArrayList<CompanyDTO> companyList = companyService.getCompanyList();
		Gson gson = new Gson();
		String companyListString = gson.toJson(companyList);
		model.addAttribute("companyList", companyListString);
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		model.addAttribute("copy", copy);
		return "estimateSystem/estimateSheet1/writeEstimateSheet1";
	}
	@RequestMapping(value = "/all/writeEstimateSheet1", method = RequestMethod.POST)
	public String writeEstimateSheet12(HttpSession session, Model model, String copy) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		ArrayList<CompanyDTO> companyList = companyService.getCompanyList();
		Gson gson = new Gson();
		String companyListString = gson.toJson(companyList);
		model.addAttribute("companyList", companyListString);
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		model.addAttribute("copy", copy);
		return "estimateSystem/estimateSheet1/writeEstimateSheet1";
	}
	
	@ResponseBody
	@RequestMapping(value = "/all/insertEstimateSheet1", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> insertEstimateSheet1(HttpSession session, Model model, EstimateSheet1DTO estimateSheet1, EstimateSheet1ItemsRecieveDTO estimateSheet1ItemsReciever) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("EstimateController.insertEstimateSheet1,user:{}",user.getUserNum());
		
		//채번
		String documentNum = estimateService.getDocoumentNum();
		//기본정보등록
		estimateSheet1.setDocumentNum(documentNum);
		estimateSheet1.setUpdater(user.getUserNum());
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		int result1 = estimateService.insertEstimateSheet1(estimateSheet1);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "見積基本情報格納エラー");
			return result;
		}
		//아이템등록
		estimateSheet1ItemsReciever.setDocumentNum(documentNum);
		int result2 = estimateService.insertEstimateSheet1Items(estimateSheet1ItemsReciever);
		if (result2 ==0) {
			result.put("errorFlag", "1");
			result.put("error", "見積ITEM情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", documentNum);
		
		return result;
	}
	
	
	@RequestMapping(value = "/all/modEstimateSheet1", method = RequestMethod.POST)
	public String modEstimateSheet1(HttpSession session, Model model, String documentNum, String documentTypeName) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		//state를 가져옴.
		//documentTypeName에따라 조인되는 DB가 다르니 주의!
		SystemDTO system = new SystemDTO();
		system.setDocumentNum(documentNum);
		system.setDocumentTypeName(documentTypeName);
		SystemDTO sys = estimateService.getEstimate(system);
		model.addAttribute("state", sys.getState());
		model.addAttribute("userNum", sys.getUserNum());
		model.addAttribute("documentNum", documentNum);
		
		ArrayList<CompanyDTO> companyList = companyService.getCompanyList();
		Gson gson = new Gson();
		String companyListString = gson.toJson(companyList);
		model.addAttribute("companyList", companyListString);
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		return "estimateSystem/estimateSheet1/modEstimateSheet1";
	}
	
	
	@RequestMapping(value = "/all/copyEstimateSheet1", method = RequestMethod.POST)
	public String copyEstimateSheet1(HttpSession session, Model model, String documentNum, String documentTypeName) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("EstimateController.copyEstimateSheet1,user:{}",user.getUserNum());
		
		//아이템도 가져오기 DTO만들어야함.
		
		//서비스로 불러서 꾸민다음에 인서트
		//채번
		//String newDocumentNum = estimateService.getDocoumentNum();
		//기본정보등록
		//estimateSheet1.setDocumentNum(newDocumentNum);
		//estimateSheet1.setUpdater(user.getUserNum());
		
		
		return "redirect:/all/estimateList";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/all/updateEstimateSheet1", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateEstimateSheet1(HttpSession session, Model model, EstimateSheet1DTO estimateSheet1, EstimateSheet1ItemsRecieveDTO estimateSheet1ItemsReciever) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		System.out.println(estimateSheet1);
		System.out.println(estimateSheet1ItemsReciever);
		
		HashMap<String, String> result = new HashMap<String, String>();
		//기본정보등록
		int result1 = estimateService.updateEstimateSheet1(estimateSheet1);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "見積基本情報格納エラー");
			return result;
		}
		//아이템등록
		estimateSheet1ItemsReciever.setDocumentNum(estimateSheet1.getDocumentNum());
		int result2 = estimateService.updateEstimateSheet1Items(estimateSheet1ItemsReciever);
		if (result2 ==0) {
			result.put("errorFlag", "1");
			result.put("error", "見積ITEM情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", estimateSheet1.getDocumentNum());
		return result;
	}
//-----------------------------------------------------------------------------------------------------------------------------------
	
	
/////////////////////////////////////estimateSolution///////////////////////////////////////////////////
//write, write copy, insert, mod, update
	@RequestMapping(value = "/all/writeEstimateSolution", method = RequestMethod.GET)
	public String writeEstimateSolution(HttpSession session, Model model, String copy) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		ArrayList<CompanyDTO> companyList = companyService.getCompanyList();
		Gson gson = new Gson();
		String companyListString = gson.toJson(companyList);
		model.addAttribute("companyList", companyListString);
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		model.addAttribute("copy", copy);
		return "estimateSystem/estimateSolution/writeEstimateSolution";
	}
	@RequestMapping(value = "/all/writeEstimateSolution", method = RequestMethod.POST)
	public String writeEstimateSolution2(HttpSession session, Model model, String copy) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		ArrayList<CompanyDTO> companyList = companyService.getCompanyList();
		Gson gson = new Gson();
		String companyListString = gson.toJson(companyList);
		model.addAttribute("companyList", companyListString);
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		model.addAttribute("copy", copy);
		return "estimateSystem/estimateSolution/writeEstimateSolution";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/all/insertEstimateSolution", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> insertEstimateSolution(HttpSession session, Model model, EstimateSolutionDTO estimateSolution, EstimateSolutionItemsRecieveDTO estimateSolutionItemsReciever) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("EstimateController.insertEstimateSolution,user:{}",user.getUserNum());
		
		//채번
		String documentNum = estimateService.getDocoumentNum();
		//기본정보등록
		estimateSolution.setDocumentNum(documentNum);
		estimateSolution.setUpdater(user.getUserNum());
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		int result1 = estimateService.insertEstimateSolution(estimateSolution);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "見積基本情報格納エラー");
			return result;
		}
		//아이템등록
		estimateSolutionItemsReciever.setDocumentNum(documentNum);
		int result2 = estimateService.insertEstimateSolutionItems(estimateSolutionItemsReciever);
		if (result2 ==0) {
			result.put("errorFlag", "1");
			result.put("error", "見積ITEM情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", documentNum);
		
		return result;
	}
	
	@RequestMapping(value = "/all/modEstimateSolution", method = RequestMethod.POST)
	public String modEstimateSolution(HttpSession session, Model model, String documentNum, String documentTypeName) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		//state를 가져옴.
		//documentTypeName에따라 조인되는 DB가 다르니 주의!
		SystemDTO system = new SystemDTO();
		system.setDocumentNum(documentNum);
		system.setDocumentTypeName(documentTypeName);
		SystemDTO sys = estimateService.getEstimate(system);
		model.addAttribute("state", sys.getState());
		model.addAttribute("userNum", sys.getUserNum());
		model.addAttribute("documentNum", documentNum);
		
		ArrayList<CompanyDTO> companyList = companyService.getCompanyList();
		Gson gson = new Gson();
		String companyListString = gson.toJson(companyList);
		model.addAttribute("companyList", companyListString);
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		return "estimateSystem/estimateSolution/modEstimateSolution";
	}
	
	@ResponseBody
	@RequestMapping(value = "/all/updateEstimateSolution", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateEstimateSolution(HttpSession session, Model model, EstimateSolutionDTO estimateSolution, EstimateSolutionItemsRecieveDTO estimateSolutionItemsReciever) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		System.out.println(estimateSolution);
		System.out.println(estimateSolutionItemsReciever);
		
		HashMap<String, String> result = new HashMap<String, String>();
		//기본정보등록
		int result1 = estimateService.updateEstimateSolution(estimateSolution);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "見積基本情報格納エラー");
			return result;
		}
		//아이템등록
		estimateSolutionItemsReciever.setDocumentNum(estimateSolution.getDocumentNum());
		int result2 = estimateService.updateEstimateSolutionItems(estimateSolutionItemsReciever);
		if (result2 ==0) {
			result.put("errorFlag", "1");
			result.put("error", "見積ITEM情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", estimateSolution.getDocumentNum());
		return result;
	}
//-----------------------------------------------------------------------------------------------------------------------------------	

	
	
	
	
	
	
/////////////////////////////////////estimateLanguage///////////////////////////////////////////////////
//write, write copy, insert, mod, update
	@RequestMapping(value = "/all/writeEstimateLanguage", method = RequestMethod.GET)
	public String writeEstimateLanguage(HttpSession session, Model model, String copy) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		ArrayList<AccountDTO> accountList = accountService.getAccountList();
		ArrayList<CompanyDTO> companyList = companyService.getCompanyList();
		Gson gson = new Gson();
		String accountListString = gson.toJson(accountList);
		String companyListString = gson.toJson(companyList);
		model.addAttribute("accountList", accountListString);
		model.addAttribute("companyList", companyListString);
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		model.addAttribute("copy", copy);
		return "estimateSystem/estimateLanguage/writeEstimateLanguage";
	}
	@RequestMapping(value = "/all/writeEstimateLanguage", method = RequestMethod.POST)
	public String writeEstimateLanguage2(HttpSession session, Model model, String copy) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		ArrayList<AccountDTO> accountList = accountService.getAccountList();
		ArrayList<CompanyDTO> companyList = companyService.getCompanyList();
		Gson gson = new Gson();
		String accountListString = gson.toJson(accountList);
		String companyListString = gson.toJson(companyList);
		model.addAttribute("accountList", accountListString);
		model.addAttribute("companyList", companyListString);
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		model.addAttribute("copy", copy);
		return "estimateSystem/estimateLanguage/writeEstimateLanguage";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/all/insertEstimateLanguage", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> insertEstimateLanguage(HttpSession session, Model model, EstimateLanguageDTO estimateLanguage, EstimateLanguageItemsRecieveDTO estimateLanguageItemsReciever) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("EstimateController.insertEstimateLanguage,user:{}",user.getUserNum());
		
		//채번
		String documentNum = estimateService.getDocoumentNum();
		//기본정보등록
		estimateLanguage.setDocumentNum(documentNum);
		estimateLanguage.setUpdater(user.getUserNum());
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		int result1 = estimateService.insertEstimateLanguage(estimateLanguage);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "見積基本情報格納エラー");
			return result;
		}
		//아이템등록
		estimateLanguageItemsReciever.setDocumentNum(documentNum);
		int result2 = estimateService.insertEstimateLanguageItems(estimateLanguageItemsReciever);
		if (result2 ==0) {
			result.put("errorFlag", "1");
			result.put("error", "見積ITEM情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", documentNum);
		
		return result;
	}
	
	@RequestMapping(value = "/all/modEstimateLanguage", method = RequestMethod.POST)
	public String modEstimateLanguage(HttpSession session, Model model, String documentNum, String documentTypeName) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		//state를 가져옴.
		//documentTypeName에따라 조인되는 DB가 다르니 주의!
		SystemDTO system = new SystemDTO();
		system.setDocumentNum(documentNum);
		system.setDocumentTypeName(documentTypeName);
		SystemDTO sys = estimateService.getEstimate(system);
		model.addAttribute("state", sys.getState());
		model.addAttribute("userNum", sys.getUserNum());
		model.addAttribute("documentNum", documentNum);
		
		ArrayList<AccountDTO> accountList = accountService.getAccountList();
		ArrayList<CompanyDTO> companyList = companyService.getCompanyList();
		Gson gson = new Gson();
		String accountListString = gson.toJson(accountList);
		String companyListString = gson.toJson(companyList);
		model.addAttribute("accountList", accountListString);
		model.addAttribute("companyList", companyListString);
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		return "estimateSystem/estimateLanguage/modEstimateLanguage";
	}
	
	@ResponseBody
	@RequestMapping(value = "/all/updateEstimateLanguage", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateEstimateLanguage(HttpSession session, Model model, EstimateLanguageDTO estimateLanguage, EstimateLanguageItemsRecieveDTO estimateLanguageItemsReciever) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		System.out.println(estimateLanguage);
		System.out.println(estimateLanguageItemsReciever);
		
		HashMap<String, String> result = new HashMap<String, String>();
		//기본정보등록
		int result1 = estimateService.updateEstimateLanguage(estimateLanguage);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "見積基本情報格納エラー");
			return result;
		}
		//아이템등록
		estimateLanguageItemsReciever.setDocumentNum(estimateLanguage.getDocumentNum());
		int result2 = estimateService.updateEstimateLanguageItems(estimateLanguageItemsReciever);
		if (result2 ==0) {
			result.put("errorFlag", "1");
			result.put("error", "見積ITEM情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", estimateLanguage.getDocumentNum());
		return result;
	}
//-----------------------------------------------------------------------------------------------------------------------------------	
	
	
	
/////////////////////////////////////estimateSi///////////////////////////////////////////////////
//write, write copy, insert, mod, update	
	@RequestMapping(value = "/all/writeEstimateSi", method = RequestMethod.GET)
	public String writeEstimateSi(HttpSession session, Model model, String copy) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		ArrayList<AccountDTO> accountList = accountService.getAccountList();
		ArrayList<CompanyDTO> companyList = companyService.getCompanyList();
		Gson gson = new Gson();
		String accountListString = gson.toJson(accountList);
		String companyListString = gson.toJson(companyList);
		model.addAttribute("accountList", accountListString);
		model.addAttribute("companyList", companyListString);
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		model.addAttribute("copy", copy);
		return "estimateSystem/estimateSi/writeEstimateSi";
	}
	@RequestMapping(value = "/all/writeEstimateSi", method = RequestMethod.POST)
	public String writeEstimateSi2(HttpSession session, Model model, String copy) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		ArrayList<AccountDTO> accountList = accountService.getAccountList();
		ArrayList<CompanyDTO> companyList = companyService.getCompanyList();
		Gson gson = new Gson();
		String accountListString = gson.toJson(accountList);
		String companyListString = gson.toJson(companyList);
		model.addAttribute("accountList", accountListString);
		model.addAttribute("companyList", companyListString);
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		model.addAttribute("copy", copy);
		return "estimateSystem/estimateSi/writeEstimateSi";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/all/insertEstimateSi", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> insertEstimateSi(HttpSession session, Model model, EstimateSiDTO estimateSi, EstimateSiItemsRecieveDTO estimateSiItemsReciever) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("EstimateController.insertEstimateSi,user:{}",user.getUserNum());
		
		//채번
		String documentNum = estimateService.getDocoumentNum();
		//기본정보등록
		estimateSi.setDocumentNum(documentNum);
		estimateSi.setUpdater(user.getUserNum());
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		int result1 = estimateService.insertEstimateSi(estimateSi);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "見積基本情報格納エラー");
			return result;
		}
		//아이템등록
		estimateSiItemsReciever.setDocumentNum(documentNum);
		int result2 = estimateService.insertEstimateSiItems(estimateSiItemsReciever);
		if (result2 ==0) {
			result.put("errorFlag", "1");
			result.put("error", "見積ITEM情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", documentNum);
		
		return result;
	}
	
	@RequestMapping(value = "/all/modEstimateSi", method = RequestMethod.POST)
	public String modEstimateSi(HttpSession session, Model model, String documentNum, String documentTypeName) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		//state를 가져옴.
		//documentTypeName에따라 조인되는 DB가 다르니 주의!
		SystemDTO system = new SystemDTO();
		system.setDocumentNum(documentNum);
		system.setDocumentTypeName(documentTypeName);
		SystemDTO sys = estimateService.getEstimate(system);
		model.addAttribute("state", sys.getState());
		model.addAttribute("userNum", sys.getUserNum());
		model.addAttribute("documentNum", documentNum);
		
		ArrayList<AccountDTO> accountList = accountService.getAccountList();
		ArrayList<CompanyDTO> companyList = companyService.getCompanyList();
		Gson gson = new Gson();
		String accountListString = gson.toJson(accountList);
		String companyListString = gson.toJson(companyList);
		model.addAttribute("accountList", accountListString);
		model.addAttribute("companyList", companyListString);
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		return "estimateSystem/estimateSi/modEstimateSi";
	}
	
	@ResponseBody
	@RequestMapping(value = "/all/updateEstimateSi", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateEstimateSi(HttpSession session, Model model, EstimateSiDTO estimateSi, EstimateSiItemsRecieveDTO estimateSiItemsReciever) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		System.out.println(estimateSi);
		System.out.println(estimateSiItemsReciever);
		
		HashMap<String, String> result = new HashMap<String, String>();
		//기본정보등록
		int result1 = estimateService.updateEstimateSi(estimateSi);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "見積基本情報格納エラー");
			return result;
		}
		//아이템등록
		estimateSiItemsReciever.setDocumentNum(estimateSi.getDocumentNum());
		int result2 = estimateService.updateEstimateSiItems(estimateSiItemsReciever);
		if (result2 ==0) {
			result.put("errorFlag", "1");
			result.put("error", "見積ITEM情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", estimateSi.getDocumentNum());
		return result;
	}
//-----------------------------------------------------------------------------------------------------------------------------------	

}
