package jp.co.interline.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import jp.co.interline.dto.DocumentMasterDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.estimateSystem.EstimateCommonDTO;
import jp.co.interline.dto.estimateSystem.estimateLanguage.EstimateLanguageDTO;
import jp.co.interline.dto.estimateSystem.estimateSi.EstimateSiDTO;
import jp.co.interline.dto.estimateSystem.estimateSolution.EstimateSolutionDTO;
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
	
	private static final Logger logger = LoggerFactory.getLogger(EstimateController.class);
	/*
	 * option:order by절에 들어갈 스트링
	 * page: pageNavigator를 위한 page수
	 */
	@RequestMapping(value = "/all/estimateList", method = RequestMethod.GET)
	public String estimateList(HttpSession session, Model model,String flagObj, 
			@RequestParam(value="option", defaultValue="e.updateDate desc")String option,
			@RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="countPerPage", defaultValue="20") int countPerPage) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("EstimateController.esimateList,user:{}",user.getUserNum());
		
		ArrayList<EstimateListDTO> estimateList = estimateService.getEstimateList(model, user,option,page,countPerPage);
		model.addAttribute("estimateList", estimateList);
		model.addAttribute("option", option);
		model.addAttribute("flagObj", flagObj);
		model.addAttribute("countPerPage", countPerPage);
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
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
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
	
	//DB상 Document를 복사하는것이 아니라. 해당 document의 내용을 그대로 해당타입(서식)의 작성화면으로 보낸다.
	@RequestMapping(value = "/all/copyDocument", method = RequestMethod.POST)
	public String copyDocument(HttpSession session, Model model, String documentNum, RedirectAttributes redirectAttributes) throws JsonProcessingException {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("EstimateController.copyDocument,user:{}",user.getUserNum());
		DocumentMasterDTO document = estimateService.getDocumentMaster(documentNum);
		HashMap<String,String> contents = estimateService.getDocumentToHashMap(document);
		ArrayList<HashMap<String,Object>> items = estimateService.getItemsToList(document);
		
		//아이템list를 map화 해주고 contents에 추가해주는 작업. (작성ozr화면에서는 item항목명+index로 동작하기때문에.)
		estimateService.makeHashMap(contents,items);
		ObjectMapper mapper = new ObjectMapper();
		String copy = mapper.writeValueAsString(contents);
		String documentTypeName = document.getDocumentTypeName();
		redirectAttributes.addAttribute("copy", copy);
		redirectAttributes.addAttribute("documentTypeName", documentTypeName);
		return "redirect:write"+documentTypeName.substring(0, 1).toUpperCase()+documentTypeName.substring(1);
	}
	
	public String getWriteDocumentPage(HttpSession session, Model model, String copy, String documentTypeName) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		Gson gson = new Gson();
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		model.addAttribute("copy", copy);
		return "estimateSystem/"+documentTypeName+"/write"+documentTypeName.substring(0, 1).toUpperCase()+documentTypeName.substring(1);
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
		model.addAttribute("documentNum", documentNum);
		
		Gson gson = new Gson();
		String userString = gson.toJson(user);
		model.addAttribute("user", userString);
		return "estimateSystem/"+documentTypeName+"/mod"+documentTypeName.substring(0, 1).toUpperCase()+documentTypeName.substring(1);
	}
	
	@RequestMapping(value = {"/all/writeEstimateSolution","/all/writeEstimateLanguage","/all/writeEstimateSi"}, method = RequestMethod.GET)
	public String writeEstimateSolution(HttpSession session, Model model, String copy, String documentTypeName) {
		return getWriteDocumentPage(session, model, copy, documentTypeName);
	}
	
	@RequestMapping(value = {"/all/modEstimateSolution","/all/modEstimateLanguage","/all/modEstimateSi"}, method = RequestMethod.POST)
	public String modEstimateSolution(HttpSession session, Model model, String documentNum, String documentTypeName) {
		return getModDocumentPage(session, model, documentNum, documentTypeName);
	}
//-----------------------------------------------------------------------------------------------------------------------------------
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////estimateSolution///////////////////////////////////////////////////
	@ResponseBody
	@RequestMapping(value = "/all/insertEstimateSolution", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> insertEstimateSolution(HttpSession session, Model model, String jsonStr, String documentTypeName) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("EstimateController.insertEstimateSolution,user:{}",user.getUserNum());
		return estimateService.insertDocument(user, session, model, jsonStr, EstimateSolutionDTO.class);
	}
	@ResponseBody
	@RequestMapping(value = "/all/updateEstimateSolution", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateEstimateSolution(HttpSession session, Model model, String jsonStr) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.updateEstimateSolution,user:{}",user.getUserNum());
		return estimateService.updateDocument(user, session, model, jsonStr, EstimateSolutionDTO.class);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////estimateLanguage///////////////////////////////////////////////////////
	@ResponseBody
	@RequestMapping(value = "/all/insertEstimateLanguage", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> insertEstimateLanguage(HttpSession session, Model model, String jsonStr) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("EstimateController.insertEstimateLanguage,user:{}",user.getUserNum());
		return estimateService.insertDocument(user, session, model, jsonStr, EstimateLanguageDTO.class);
	}
	
	@ResponseBody
	@RequestMapping(value = "/all/updateEstimateLanguage", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateEstimateLanguage(HttpSession session, Model model, String jsonStr) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.updateEstimateLanguage,user:{}",user.getUserNum());
		return estimateService.updateDocument(user, session, model, jsonStr, EstimateLanguageDTO.class);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////estimateSi/////////////////////////////////////////////////////////
	@ResponseBody
	@RequestMapping(value = "/all/insertEstimateSi", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> insertEstimateSi(HttpSession session, Model model, String jsonStr) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("EstimateController.insertEstimateSi,user:{}",user.getUserNum());
		return estimateService.insertDocument(user, session, model, jsonStr, EstimateSiDTO.class);
	}
	
	@ResponseBody
	@RequestMapping(value = "/all/updateEstimateSi", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public HashMap<String, String> updateEstimateSi(HttpSession session, Model model, String jsonStr) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("BillController.updateEstimateSi,user:{}",user.getUserNum());
		return estimateService.updateDocument(user, session, model, jsonStr, EstimateSiDTO.class);
	}
	
//-----------------------------------------------------------------------------------------------------------------------------------	
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@RequestMapping(value = "/all/test", method = RequestMethod.GET)
	public String test(HttpSession session, Model model, String documentNum) throws Exception {
		System.out.println(TransactionSynchronizationManager.getCurrentTransactionName());
		estimateService.test("1");
		estimateService.test2("1");
		return "";
	}
	
	
}
