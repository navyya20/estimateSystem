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

import jp.co.interline.dto.CompanyDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.AccountDTO;
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
	@Autowired
	AccountService accountService;
	@Autowired
	CompanyService companyService;
	
	private static final Logger logger = LoggerFactory.getLogger(BillController.class);
	
	/*
	 * option:order by절에 들어갈 스트링
	 * page: pageNavigator를 위한 page수
	 */
	@RequestMapping(value = "/all/billList", method = RequestMethod.GET)
	public String billList(HttpSession session, Model model, @RequestParam(value="option", defaultValue="b.updateDate desc")String option, @RequestParam(value="page", defaultValue="1") int page) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.esimateList,user:{}",user.getUserNum());
		
		ArrayList<EstimateListDTO> billList = billService.getBillList(model, user,option,page);
		
		model.addAttribute("billList", billList);
		model.addAttribute("option", option);
		//네비게이션에대한 모델은 서비스에서 넣어준다.
		return "billSystem/billList";
	}
	
	@RequestMapping(value = "/all/selectBill", method = RequestMethod.GET)
	public String selectBill(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.selectBill,user:{}",user.getUserNum());
		
		return "billSystem/billSelect";
	}

	
///////////////////////////billSheet1//////////////////////////////////////////////////
//write,insert,mod,update
	@RequestMapping(value = "/all/writeBillSheet1", method = RequestMethod.GET)
	public String writeBillSheet1(HttpSession session, Model model, String estimateNum) {
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
		model.addAttribute("estimateNum", estimateNum);
		return "billSystem/billSheet1/writeBillSheet1";
	}
	
	@RequestMapping(value = "/all/writeBillSheet1", method = RequestMethod.POST)
	public String writeBillSheet1Copy(HttpSession session, Model model, String estimateNum, String copy) {
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
		model.addAttribute("estimateNum", estimateNum);
		model.addAttribute("copy", copy);
		return "billSystem/billSheet1/writeBillSheet1";
	}
	
	@ResponseBody
	@RequestMapping(value = "/all/insertBillSheet1", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> insertBillSheet1(HttpSession session, Model model, BillSheet1DTO billSheet1, BillSheet1ItemsRecieveDTO billSheet1ItemsReciever) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.insertBillSheet1,user:{}",user.getUserNum());
		//채번
		String documentNum = billService.getDocoumentNum();
		//기본정보등록
		billSheet1.setDocumentNum(documentNum);
		billSheet1.setUpdater(user.getUserNum());
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		int result1 = billService.insertBillSheet1(billSheet1);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "請求基本情報格納エラー");
			return result;
		}
		//아이템등록
		billSheet1ItemsReciever.setDocumentNum(documentNum);
		int result2 = billService.insertBillSheet1Items(billSheet1ItemsReciever);
		if (result2 ==0) {
			result.put("errorFlag", "1");
			result.put("error", "請求ITEM情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", documentNum);
		return result;
	}
	
	@RequestMapping(value = "/all/modBillSheet1", method = RequestMethod.POST)
	public String modBillSheet1(HttpSession session, Model model, String documentNum, String documentTypeName) {
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
		
		ArrayList<AccountDTO> accountList = accountService.getAccountList();
		ArrayList<CompanyDTO> companyList = companyService.getCompanyList();
		Gson gson = new Gson();
		String accountListString = gson.toJson(accountList);
		String companyListString = gson.toJson(companyList);
		model.addAttribute("accountList", accountListString);
		model.addAttribute("companyList", companyListString);
		
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		return "billSystem/billSheet1/modBillSheet1";
	}
	
	@ResponseBody
	@RequestMapping(value = "/all/updateBillSheet1", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateBillSheet1(HttpSession session, Model model, BillSheet1DTO billSheet1, BillSheet1ItemsRecieveDTO billSheet1ItemsReciever) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		
		HashMap<String, String> result = new HashMap<String, String>();
		//기본정보등록
		int result1 = billService.updateBillSheet1(billSheet1);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "請求基本情報格納エラー");
			return result;
		}
		//아이템등록
		billSheet1ItemsReciever.setDocumentNum(billSheet1.getDocumentNum());
		int result2 = billService.updateBillSheet1Items(billSheet1ItemsReciever);
		if (result2 ==0) {
			result.put("errorFlag", "1");
			result.put("error", "請求ITEM情報格納エラー");
			return result;
		}
		
		
		result.put("errorFlag", "0");
		result.put("documentNum", billSheet1.getDocumentNum());
		return result;
	}
	
	
	
	
	
/////////////////////////////billSolution//////////////////////////////////////////////////
//write,insert,mod,update	
	
	@RequestMapping(value = "/all/writeBillSolution", method = RequestMethod.GET)
	public String writeBillSolution(HttpSession session, Model model, String estimateNum) {
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
		model.addAttribute("estimateNum", estimateNum);
		return "billSystem/billSolution/writeBillSolution";
	}
	
	@RequestMapping(value = "/all/writeBillSolution", method = RequestMethod.POST)
	public String writeBillSolutionCopy(HttpSession session, Model model, String estimateNum, String copy) {
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
		model.addAttribute("estimateNum", estimateNum);
		model.addAttribute("copy", copy);
		return "billSystem/billSolution/writeBillSolution";
	}
	
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
		
		ArrayList<AccountDTO> accountList = accountService.getAccountList();
		ArrayList<CompanyDTO> companyList = companyService.getCompanyList();
		Gson gson = new Gson();
		String accountListString = gson.toJson(accountList);
		String companyListString = gson.toJson(companyList);
		model.addAttribute("accountList", accountListString);
		model.addAttribute("companyList", companyListString);
		
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		return "billSystem/billSolution/modBillSolution";
	}
	
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
	public String writeBillSi(HttpSession session, Model model, String estimateNum) {
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
		model.addAttribute("estimateNum", estimateNum);
		return "billSystem/billSi/writeBillSi";
	}
	
	@RequestMapping(value = "/all/writeBillSi", method = RequestMethod.POST)
	public String writeBillSiCopy(HttpSession session, Model model, String estimateNum, String copy) {
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
		model.addAttribute("estimateNum", estimateNum);
		model.addAttribute("copy", copy);
		return "billSystem/billSi/writeBillSi";
	}
	
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
		
		ArrayList<AccountDTO> accountList = accountService.getAccountList();
		ArrayList<CompanyDTO> companyList = companyService.getCompanyList();
		Gson gson = new Gson();
		String accountListString = gson.toJson(accountList);
		String companyListString = gson.toJson(companyList);
		model.addAttribute("accountList", accountListString);
		model.addAttribute("companyList", companyListString);
		
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		return "billSystem/billSi/modBillSi";
	}
	
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * //estimateTypeNum으로 부터 상응하는 billTypeName을 가져온다.
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/all/getBillType", method = RequestMethod.POST,
	 * produces="application/json;charset=UTF-8") public String
	 * getBillType(HttpSession session, Model model, int documentTypeNum) {
	 * UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
	 * SystemDTO billType = billService.getBillType(documentTypeNum);
	 * 
	 * return billType.getDocumentTypeName(); }
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/all/getBillTypeName", method = RequestMethod.POST,
	 * produces="application/json;charset=UTF-8") public String
	 * getBillTypeName(HttpSession session, Model model, int documentTypeNum) {
	 * UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
	 * SystemDTO billType = billService.getBillTypeName(documentTypeNum); return
	 * billType.getDocumentTypeName(); }
	 */
	
	
	
	
	
	
}
