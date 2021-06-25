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
import jp.co.interline.dto.EstimateSheet1DTO;
import jp.co.interline.dto.EstimateSheet1ItemsRecieveDTO;
import jp.co.interline.dto.FileNamesDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.WorkflowInformDTO;
import jp.co.interline.service.AccountService;
import jp.co.interline.service.BillService;
import jp.co.interline.service.CompanyService;
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
	
	//estimateTypeNum으로 부터 상응하는 billTypeName을 가져온다.
	@ResponseBody
	@RequestMapping(value = "/all/getBillType", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String getBillType(HttpSession session, Model model, int documentTypeNum) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		SystemDTO billType = billService.getBillType(documentTypeNum);
		
		return billType.getDocumentTypeName();
	}
	@ResponseBody
	@RequestMapping(value = "/all/getBillTypeName", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String getBillTypeName(HttpSession session, Model model, int documentTypeNum) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		SystemDTO billType = billService.getBillTypeName(documentTypeNum);
		return billType.getDocumentTypeName();
	}
	
	
	/*
	 * 청구서 일기 페이지
	 * 모든 문서시스템을 통들어서 state를 가져옴.
	 */
	@RequestMapping(value = "/all/readBillSheet1", method = RequestMethod.POST)
	public String readBillSheet1(HttpSession session, Model model, String documentNum, String approveMode) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		//모든 문서를 통틀어서 state를 가져옴. 새로운 시스템 추가시 sql에 유니온 필요.
		BillSheet1DTO billSheet1 = billService.getBillSheet1ByDocumentNum(documentNum);
		model.addAttribute("state", billSheet1.getState());
		model.addAttribute("userNum", billSheet1.getUserNum());
		model.addAttribute("billNum", documentNum);
		model.addAttribute("approveMode", approveMode);
		return "billSystem/billSheet1/readBillSheet1";
	}
	@RequestMapping(value = "/all/modBillSheet1", method = RequestMethod.POST)
	public String modBillSheet1(HttpSession session, Model model, String documentNum) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		
		//state를 가져옴. 새로운 시스템 추가시 sql에 유니온 필요.
		BillSheet1DTO billSheet1 = billService.getBillSheet1ByDocumentNum(documentNum);
		model.addAttribute("state", billSheet1.getState());
		model.addAttribute("userNum", billSheet1.getUserNum());
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
	
	
	
}
