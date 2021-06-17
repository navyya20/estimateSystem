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
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.WorkflowDTO;
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
	@Autowired
	CompanyService companyService;
	
	private static final Logger logger = LoggerFactory.getLogger(WorkflowController.class);
	
	@RequestMapping(value = "/all/workflowList", method = RequestMethod.GET)
	public String workflowList(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		
		String where = "order by department asc, position asc";
		ArrayList<UserInformDTO> userList =  userServive.getUserListOrderd(where);
		userList = userServive.getUserListOrderdOnlyAdmin(where);
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
		
		//타겟키를 aaa로 고정시킴 필요없을듯
		//int r2= workflowService.updateTarget(systemNum);
		
		HashMap<String,String> result = new HashMap<>();
		if(r1!=1) {
			result.put("error","処理エラー");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/all/requestApproval", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String requestApproval(HttpSession session, Model model, int documentTypeNum, String documentTypeName, String documentNum, int systemNum) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		System.out.println(documentTypeNum);
		System.out.println(documentTypeName);
		System.out.println(documentNum);
		System.out.println(systemNum);
		
		//시스템에 해당하는 워크플로우 인폼 가져오기
		WorkflowInformDTO workflowInform = workflowService.getWorkflowInformBySystemNum(systemNum);
		
		//워크플로우 꾸미기 및 인서트
		int workflowNum = workflowService.insertWorkflow(workflowInform,documentTypeNum,documentTypeName,documentNum,user);
		
		//문서에 워크플로우등록
		WorkflowDTO document = new WorkflowDTO();
		document.setDocumentNum(documentNum);
		document.setDocumentTypeName(documentTypeName);
		document.setWorkflowNum(workflowNum);
		int result0 =  workflowService.updateWorkflowNum(document);
		
		//워크플로우 최신화(승인이 일어났을때, 최초 인서트시  실행시켜야함.)
		int result1 = workflowService.renewWorkflow(workflowNum,user);
		
		//워크플로우 최종승인확인.
		Boolean state = workflowService.confirmation(workflowNum);
		int result2=0;
		int result3=0;
		if (state == true) {
			//문서 상태를 app로
			WorkflowDTO workflow = new WorkflowDTO();
			workflow.setDocumentTypeName(documentTypeName);
			workflow.setDocumentNum(documentNum);
			workflow.setState("app");
			result2 =  workflowService.updateState(workflow);
			//도장 로고
			FileNamesDTO stampFileName = companyService.getfileName("stamp");
			FileNamesDTO logoFileName = companyService.getfileName("logo");
			SystemDTO system = new SystemDTO();
			system.setStampFileName(stampFileName.getFileName());
			system.setLogoFileName(logoFileName.getFileName());
			system.setDocumentTypeName(documentTypeName);
			system.setDocumentNum(documentNum);
			result3=workflowService.stampConfirm(system);
		}else {
			//문서상태 req로
			WorkflowDTO workflow = new WorkflowDTO();
			workflow.setDocumentTypeName(documentTypeName);
			workflow.setDocumentNum(documentNum);
			workflow.setState("req");
			result2 =  workflowService.updateState(workflow);
		}
		if (result2!=1) {
			System.out.println(result2);
			System.out.println("문서상태update문제");
		}
		
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/all/getWatingWorkflow", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String getWatingWorkflow(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		int result = workflowService.getNumberOfWorkflows(user.getUserNum());
		return result+"";
	}
	
	
	@RequestMapping(value = "/all/workflowWaitingList", method = RequestMethod.GET)
	public String workflowWaitingList(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		ArrayList<WorkflowDTO> workflowList = workflowService.getWorkflowWaitingList(user.getUserNum());
		System.out.println(workflowList);
		model.addAttribute("workflowList", workflowList);
		return "approval/workflowWaitingList";
	}
	
	@ResponseBody
	@RequestMapping(value = "/all/approval", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String approval(HttpSession session, Model model, String documentTypeName, String documentNum, int workflowNum) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		WorkflowDTO workflow = workflowService.getWorkflowByDocumentNum(documentNum);
		if (user.getUserNum()!= workflow.getPresentApprover()) {
			return "承認権限がありません。";
		}
		
		//승인처리
		int result0 = workflowService.updateApprove(workflow,user);
		//워크플로우 정보 갱신
		int result1 = workflowService.renewWorkflow(workflow.getWorkflowNum(),user);
		//워크플로우 최종승인확인.
		Boolean state = workflowService.confirmation(workflowNum);
		int result2=0;
		int result3=0;
		if (state == true) {
			//문서 상태를 app로
			WorkflowDTO w = new WorkflowDTO();
			w.setDocumentTypeName(documentTypeName);
			w.setDocumentNum(workflow.getDocumentNum());
			w.setState("app");
			result2 =  workflowService.updateState(w);
			//도장 찍기, 로고 확정
			FileNamesDTO stampFileName = companyService.getfileName("stamp");
			FileNamesDTO logoFileName = companyService.getfileName("logo");
			SystemDTO system = new SystemDTO();
			system.setStampFileName(stampFileName.getFileName());
			system.setLogoFileName(logoFileName.getFileName());
			system.setDocumentTypeName(documentTypeName);
			system.setDocumentNum(documentNum);
			result3=workflowService.stampConfirm(system);
		}else {
			//문서상태 req로
			WorkflowDTO w = new WorkflowDTO();
			w.setDocumentTypeName(documentTypeName);
			w.setDocumentNum(workflow.getDocumentNum());
			w.setState("req");
			result2 =  workflowService.updateState(w);
		}
		if (result2!=1) {
			System.out.println(result2);
			return "承認はできましたが、文書状態を更新に失敗しました。";
		}
		
		return "承認完了";
	}
	
	@ResponseBody
	@RequestMapping(value = "/all/reject", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String reject(HttpSession session, Model model, String documentTypeName, String documentNum, int workflowNum) {
		//workflow삭제 문서의 workflowNum은 자동으로 null
		int result1 = workflowService.deleteWorkflow(workflowNum);
		
		
		//문서상태 wri로
		WorkflowDTO w = new WorkflowDTO();
		w.setDocumentTypeName(documentTypeName);
		w.setDocumentNum(documentNum);
		w.setState("wri");
		int result2 =  workflowService.updateState(w);
		if (result2 != 1) {
			return "差し戻し中エラーが発生しました。管理者にお問い合わせください。";
		}
		
		return "差し戻し完了";
	}
}
