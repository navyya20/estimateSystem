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

import jp.co.interline.dto.BillSheet1DTO;
import jp.co.interline.dto.CompanyDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.EstimateSheet1DTO;
import jp.co.interline.dto.EstimateSheet1ItemsRecieveDTO;
import jp.co.interline.dto.FileNamesDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.WorkflowInformDTO;
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
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(EstimateController.class);
	
	@RequestMapping(value = "/all/writeEstimateSheet1", method = RequestMethod.GET)
	public String writeEstimateSheet1(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		ArrayList<CompanyDTO> companyList = companyService.getCompanyList();
		Gson gson = new Gson();
		String companyListString = gson.toJson(companyList);
		model.addAttribute("companyList", companyListString);
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		return "estimateSystem/estimateSheet1/writeEstimateSheet1";
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/all/insertEstimateSheet1", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> insertEstimateSheet1(HttpSession session, Model model, EstimateSheet1DTO estimateSheet1, EstimateSheet1ItemsRecieveDTO estimateSheet1ItemsReciever) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
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
	
	
	@RequestMapping(value = "/all/estimateList", method = RequestMethod.GET)
	public String estimateList(HttpSession session, Model model, @RequestParam(value="option", defaultValue="e.updateDate desc")String option, @RequestParam(value="page", defaultValue="1") int page) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		
		ArrayList<EstimateListDTO> estimateList = estimateService.getEstimateList(model, user,option,page);
		
		
		model.addAttribute("estimateList", estimateList);
		model.addAttribute("option", option);
		//네비게이션에대한 모델은 서비스에서 넣어준다.
		return "estimateSystem/estimateList";
	}
	
	
	@RequestMapping(value = "/all/readEstimateSheet1", method = RequestMethod.POST)
	public String readEstimateSheet1(HttpSession session, Model model, String documentNum, String approveMode) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		EstimateSheet1DTO estimateSheet1 = estimateService.getEstimateSheet1ByDocumentNum(documentNum);
		//모든 문서를 통틀어서 state를 가져옴. 새로운 시스템 추가시 sql에 유니온 필요.
		model.addAttribute("state", estimateSheet1.getState());
		model.addAttribute("userNum", estimateSheet1.getUserNum());
		model.addAttribute("estimateNum", documentNum);
		model.addAttribute("approveMode", approveMode);
		return "estimateSystem/estimateSheet1/readEstimateSheet1";
	}
	
	@RequestMapping(value = "/all/modEstimateSheet1", method = RequestMethod.POST)
	public String modEstimateSheet1(HttpSession session, Model model, String documentNum) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		String state = workflowService.getState(documentNum);
		//모든 문서를 통틀어서 state를 가져옴. 새로운 시스템 추가시 sql에 유니온 필요.
		model.addAttribute("state", state);
		model.addAttribute("estimateNum", documentNum);
		
		ArrayList<CompanyDTO> companyList = companyService.getCompanyList();
		Gson gson = new Gson();
		String companyListString = gson.toJson(companyList);
		model.addAttribute("companyList", companyListString);
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		return "estimateSystem/estimateSheet1/modEstimateSheet1";
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
	
	@ResponseBody
	@RequestMapping(value = "/all/deleteSheets", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateEstimateSheet1(HttpSession session, Model model, String[] documentArr) {
		for (int i = 0; i < documentArr.length; i++) {
			System.out.println(documentArr[i]);
			String[] arr = documentArr[i].split("\\^");
			String documentNum = arr[0];
			String documentTypeName = arr[1];
			estimateService.deleteSheet(documentNum,documentTypeName);
		}
		return null;
	}
	
}
