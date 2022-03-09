package jp.co.interline.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.WorkflowDTO;
import jp.co.interline.dto.WorkflowInformDTO;
import jp.co.interline.service.CompanyService;
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
	
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@ResponseBody
	@RequestMapping(value = "/all/requestApproval", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String requestApproval(HttpSession session, Model model, String documentTypeName, String documentNum, int systemNum) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		//시스템에 해당하는 워크플로우 인폼 가져오기
		WorkflowInformDTO workflowInform = workflowService.getWorkflowInformBySystemNum(systemNum);
		
		//워크플로우 꾸미기 및 인서트
		int workflowNum = workflowService.insertWorkflow(workflowInform,documentTypeName,documentNum,user);
		if (workflowNum == 0) {return "workflow生成失敗";}
		//문서에 워크플로우등록
		WorkflowDTO document = new WorkflowDTO();
		document.setDocumentNum(documentNum);
		document.setDocumentTypeName(documentTypeName);
		document.setWorkflowNum(workflowNum);
		int result0 =  workflowService.updateWorkflowNum(document);
		if (result0 ==0) {return "workflow登録失敗";}
		
		//워크플로우 최신화(승인이 일어났을때, 최초 인서트시  실행시켜야함.)
		int result1 = workflowService.renewWorkflow(workflowNum,user);
		if (result1==0) {return "workflow更新失敗";}
		//워크플로우 최종승인확인.
		Boolean result2 = workflowService.confirmation(workflowNum,documentNum,documentTypeName);
		if (result2==false) {return "承認確認失敗";}
		
		return "承認依頼完了";
	}
	
	
	//내 앞으로 승인대기중인 문서들 리스트가 몇건인지 불러온다.
	@ResponseBody
	@RequestMapping(value = "/all/getWaitingWorkflow", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String getWaitingWorkflow(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		int result = workflowService.getNumberOfWorkflows(user.getUserNum());
		return result+"";
	}
	
	//내 앞으로 승인대기중인 문서들 리스트를 불러온다.
	@RequestMapping(value = "/all/workflowWaitingList", method = RequestMethod.GET)
	public String workflowWaitingList(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		ArrayList<WorkflowDTO> workflowList = workflowService.getWorkflowWaitingList(user.getUserNum());
		//System.out.println(workflowList);
		model.addAttribute("workflowList", workflowList);
		return "approval/workflowWaitingList";
	}
	
	/*
	 * 1.workflow의 presentApproverNum번째의 approveState#를 a로 바꾼다.
	 * 2.바뀐 approve#State로 새롭게 presentApprover와 presentApproverNum을 설정한다.
	 * 3.만약 approveState1~3가 모두 a 즉 targetValue가 aaa라면 승인완료 처리를 한다.
	 */
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@ResponseBody
	@RequestMapping(value = "/all/approval", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String approval(HttpSession session, Model model, String documentTypeName, String documentNum, int workflowNum) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		WorkflowDTO workflow = workflowService.getWorkflowByDocumentNum(documentNum);
		//승인하려고 하는 주체가  예정된 현제승인자와 일치하지않을 경우 에러.
		if (user.getUserNum()!= workflow.getPresentApprover()) {
			return "承認権限がありません。";
		}
		
		//승인처리
		int result0 = workflowService.updateApprove(workflow,user);
		if (result0==0) {return "承認処理中問題発生しました。";}
		//워크플로우 정보 갱신
		int result1 = workflowService.renewWorkflow(workflow.getWorkflowNum(),user);
		if (result1==0) {return "workflow更新失敗";}
		//워크플로우 최종승인확인.
		Boolean result2 = workflowService.confirmation(workflowNum,documentNum,documentTypeName);
		if (result2=false) {return "承認確認失敗";}
		
		return "承認完了";
	}
	
	//승인거절한다.
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@ResponseBody
	@RequestMapping(value = "/all/reject", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String reject(HttpSession session, Model model, String documentTypeName, String documentNum, int workflowNum) {
		//workflow삭제 문서에 저장됬던 workflowNum은 자동으로 null
		int result1 = workflowService.deleteWorkflow(workflowNum);
		
		//문서상태 wri로
		int result2 =  workflowService.updateStateWRI(documentNum);
		if (result2 != 1) {
			return "差し戻し中エラーが発生しました。管理者にお問い合わせください。";
		}
		
		return "差し戻し完了";
	}
}
