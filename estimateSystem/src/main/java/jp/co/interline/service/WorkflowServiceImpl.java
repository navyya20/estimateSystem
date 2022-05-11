package jp.co.interline.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.co.interline.dao.CompanyDAO;
import jp.co.interline.dao.WorkflowDAO;
import jp.co.interline.dto.ApprovedListDTO;
import jp.co.interline.dto.DocumentMasterDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.FileNamesDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.UserInformWithOptionDTO;
import jp.co.interline.dto.WorkflowDTO;
import jp.co.interline.dto.WorkflowInformDTO;
import oz.scheduler.SchedulerException;

@Service
public class WorkflowServiceImpl implements WorkflowService {
	@Autowired
	WorkflowDAO workflowDao;
	@Autowired
	CompanyDAO companyDao;
	
	//private static final int countPerPage=20;	
	private static final int pagePerGroup=10;

	@Override
	public WorkflowInformDTO getWorkflowInformBySystemNum(int systemNum) {
		WorkflowInformDTO workflowInform = workflowDao.getWorkflowInformBySystemNum(systemNum);
		return workflowInform;
	}
	@Override
	public WorkflowInformDTO getWorkflowInformWithNameBySystemNum(int systemNum) {
		WorkflowInformDTO workflowInform = workflowDao.getWorkflowInformWithNameBySystemNum(systemNum);
		return workflowInform;
	}

	@Override
	public int updateWorkflowInform(int userNum, int systemNum, int order) {
		WorkflowInformDTO workflowInform = new WorkflowInformDTO();
		workflowInform.setSystemNum(systemNum);
		workflowInform.setOrder(order);
		switch (order) {
		case 1:
			workflowInform.setApprover1(userNum);
			break;
		case 2:
			workflowInform.setApprover2(userNum);
			break;
		case 3:
			workflowInform.setApprover3(userNum);
			break;
		default:
			break;
		}
		
		int result=workflowDao.updateWorkflowInform(workflowInform);
		return result;
	}
	
	//필요없을듯
	@Override
	public int updateTarget(int systemNum) {
		WorkflowInformDTO workflowInform = workflowDao.getWorkflowInformBySystemNum(systemNum);
		String targetKey = "";
		if(workflowInform.getApprover1()==-1) {
			targetKey=targetKey+"n";
		}else {
			targetKey=targetKey+"a";
		}
		if(workflowInform.getApprover2()==-1) {
			targetKey=targetKey+"n";
		}else {
			targetKey=targetKey+"a";
		}
		if(workflowInform.getApprover3()==-1) {
			targetKey=targetKey+"n";
		}else {
			targetKey=targetKey+"a";
		}
		if(targetKey.length()!=3) {
			return 0;			
		}
		workflowInform.setTargetKey(targetKey);
		int result = workflowDao.setWorkflowInformTargetKey(workflowInform);
		
		return result;
		
	}
	
	
	@Override
	public String getState(String documentNum) {
		String state = workflowDao.getState(documentNum);
		return state;
	}
	
	
	//워크플로우 꾸미기 및 인서트
	//해당systemNum의 저장된 workflowNum을 가져와서 workflow객체를 꾸민다.
	@Override
	public int insertWorkflow(WorkflowInformDTO workflowInform, String documentTypeName, String documentNum, UserInformDTO user) {
		WorkflowDTO workflow = new WorkflowDTO();
		
		workflow.setSystemNum(workflowInform.getSystemNum());
		workflow.setUserNum(user.getUserNum());
		workflow.setDocumentTypeName(documentTypeName);
		workflow.setDocumentNum(documentNum);
		workflow.setApprover1(workflowInform.getApprover1());
		workflow.setApprover2(workflowInform.getApprover2());
		workflow.setApprover3(workflowInform.getApprover3());
		workflow.setApprover1State("n");
		workflow.setApprover2State("n");
		workflow.setApprover3State("n");
		workflow.setPresentApproverNum(1);
		workflow.setPresentApprover(workflowInform.getApprover1());
		workflow.setTargetKey(workflowInform.getTargetKey());
		workflow.setTargetValue("nnn");
		workflow.setUpdater(user.getUserNum());
		
		int workflowNum = workflowDao.insertWorkflow(workflow);
		return workflowNum;
	}
	@Override
	public int renewWorkflow(int workflowNum, UserInformDTO user) {
		System.out.println("workflowNum:"+workflowNum);
		WorkflowDTO w = workflowDao.getWorkflowByWorkflowNum(workflowNum);
		System.out.println(w);
		//승인자가 지정되어있지않을경우(approver가-1) approved되었다(approver#State가 a)고 친다.
		int a1 = w.getApprover1();
		int a2 = w.getApprover2();
		int a3 = w.getApprover3();
		if (a1==-1) {
			w.setApprover1State("a");
		}
		if (a2==-1) {
			w.setApprover2State("a");
		}
		if (a3==-1) {
			w.setApprover3State("a");
		}
		
		//승인이 안된단계 approverState = "n"일 경우. 
		//현제 승인대기자(presentApprover)와 현제단계(approverNum)에 해당 approver와 단계n을 넣는다.
		String as1 = w.getApprover1State();
		String as2 = w.getApprover2State();
		String as3 = w.getApprover3State();
		if (as1.equals("n")) {
			w.setPresentApprover(w.getApprover1());
			w.setPresentApproverNum(1);
		}else if (as2.equals("n")) {
			w.setPresentApprover(w.getApprover2());
			w.setPresentApproverNum(2);
		}else if (as3.equals("n")) {
			w.setPresentApprover(w.getApprover3());
			w.setPresentApproverNum(3);
		}else {
			//1,2,3단계 모두 승인상태인경우 현제 승인대기자(presentApprover)와 현제단계(approverNum)에 false를 의미하는  -1을 설정.
			w.setPresentApprover(-1);
			w.setPresentApproverNum(-1);
		}
		
		//각단계의 승인상태를 합쳐서 targetValue에 저장한다. ex) ana, nnn, aaa등등. confirmation에서 aaa가 확인되면 최종승인이된다.
		String targetValue = as1+as2+as3;
		w.setTargetValue(targetValue);
		w.setWorkflowNum(workflowNum);
		w.setUpdater(user.getUserNum());
		int result = workflowDao.renewWorkflow(w);
		
		return result;
	}
	@Override
	public Boolean confirmation(int workflowNum, String documentNum, String documentTypeName, int systemNum) {
		WorkflowDTO w = workflowDao.getWorkflowByWorkflowNum(workflowNum);
		//TargetKey는 aaa고정이다. workflowInform을 만들때 승인자가 없을경우를 대비해 aaa이외의 상태도 고려하였지만, 
		//승인자가 없을경우 renew메서드에서 n->a로 바꿔주기때문에  aaa로 통일하면된다.
		//TargetKey("aaa") == TargetValue("aaa")이면 승인이 완료된 상태라는 뜻으로 승인완료절차 진행. 
		boolean state = w.getTargetKey().equals(w.getTargetValue());
		if (state == true) {
			//문서 상태를 승인완료(app)로
			WorkflowDTO workflow = new WorkflowDTO();
			workflow.setDocumentNum(documentNum);
			workflow.setState("app");
			int result1 =  workflowDao.updateState(workflow);
			if (result1==0) {return false;}
			//도장 로고
			FileNamesDTO stampFileName = companyDao.getfileName("stamp");
			FileNamesDTO logoFileName = companyDao.getfileName("logo");
			SystemDTO system = new SystemDTO();
			system.setStampFileName(stampFileName.getFileName());
			system.setLogoFileName(logoFileName.getFileName());
			system.setDocumentTypeName(documentTypeName);
			system.setDocumentNum(documentNum);
			int result2=workflowDao.stampConfirm(system);
			if (result2==0) {return false;}
		}else {
			//문서상태 req로
			WorkflowDTO workflow = new WorkflowDTO();
			workflow.setDocumentNum(documentNum);
			workflow.setState("req");
			int result =  workflowDao.updateState(workflow);
			if (result==0) {return false;}
		}
		return true;
	}
	@Override
	public Boolean generateFile(int workflowNum, String documentNum, String documentTypeName, int systemNum) {
		WorkflowDTO w = workflowDao.getWorkflowByWorkflowNum(workflowNum);
		//TargetKey는 aaa고정이다. workflowInform을 만들때 승인자가 없을경우를 대비해 aaa이외의 상태도 고려하였지만, 
		//승인자가 없을경우 renew메서드에서 n->a로 바꿔주기때문에  aaa로 통일하면된다.
		//TargetKey("aaa") == TargetValue("aaa")이면 승인이 완료된 상태라는 뜻으로 승인완료절차 진행. 
		boolean state = w.getTargetKey().equals(w.getTargetValue());
		if (state == true) {
			//승인완료된 문서를 파일로 저장
			GetProperties properties = new GetProperties();
			String OZserverURL = "http://"+properties.getOzIP()+"/oz80/server";
			String schedulerIP = properties.getSchedulerIP();
			String ipScheduler;
			int portScheduler;
			if(schedulerIP.contains(":")) {
				ipScheduler = schedulerIP.split(":")[0];
				portScheduler = Integer.parseInt(schedulerIP.split(":")[1]);
			}else {
				ipScheduler = schedulerIP;
				portScheduler = 80;
			}
			ExportReport exportDocument = new ExportReport("admin", "admin1", OZserverURL, ipScheduler, portScheduler);
			String systemName = workflowDao.getSystemBySystemNum(systemNum);
			systemName.substring(0, 1);
			String jsonData = null;
			String nameOzr = systemName+"/"+documentTypeName+"/read"+documentTypeName.substring(0,1).toUpperCase()+documentTypeName.substring(1)+".ozr";
			String[] ozrParamValue = {"path=http://"+properties.getWebIP()+"/files/estimateSystem/uploaded/"};
			String nameOdi = "read"+documentTypeName.substring(0,1).toUpperCase()+documentTypeName.substring(1);
			String[] odiParamValue = {"documentNum="+documentNum};
			String fileName=documentNum;
			try {
				exportDocument.exportMethod(jsonData, nameOzr, ozrParamValue, nameOdi, odiParamValue, "pdf", fileName);
				DocumentMasterDTO documentMaster= new DocumentMasterDTO();
				documentMaster.setDocumentNum(documentNum);
				documentMaster.setFileName(fileName+".pdf");
				int result = workflowDao.setfileName(documentMaster);
				if (result == 0) {return false;}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int updateStateWRI(String documentNum) {
		//문서상태 wri로
		WorkflowDTO w = new WorkflowDTO();
		w.setDocumentNum(documentNum);
		w.setState("wri");
		int result = workflowDao.updateState(w);
		return result;
	}
	@Override
	public int updateWorkflowNum(WorkflowDTO document) {
		int result = workflowDao.updateWorkflowNum(document);
		return result;
	}
	@Override
	public int getNumberOfWorkflows(int userNum) {
		int result = workflowDao.getNumberOfWorkflows(userNum);
		return result;
	}
	@Override
	public ArrayList<WorkflowDTO> getWorkflowWaitingList(int userNum) {
		ArrayList<WorkflowDTO> workflowList = workflowDao.getWorkflowWaitingList(userNum);
		return workflowList;
	}
	@Override
	public WorkflowDTO getWorkflowByDocumentNum(String documentNum) {
		WorkflowDTO workflow = workflowDao.getWorkflowByDocumentNum(documentNum);
		return workflow;
	}
	@Override
	public int updateApprove(WorkflowDTO workflow, UserInformDTO user) {
		workflow.setUpdater(user.getUserNum());
		int order = workflow.getPresentApproverNum();
		switch (order) {
		case 1:
			workflow.setApprover1State("a");
			break;
		case 2:
			workflow.setApprover2State("a");
			break;
		case 3:
			workflow.setApprover3State("a");
			break;
		default:
			break;
		}
		int result = workflowDao.updateApprove(workflow);
		return result;
	}
	@Override
	public int deleteWorkflow(int workflowNum) {
		int result = workflowDao.deleteWorkflow(workflowNum);
		return result;
	}
	@Override
	public int stampConfirm(SystemDTO system) {
		int result = workflowDao.stampConfirm(system);
		return result;
	}
	
	
	/*
	 * option: order by절에 들어갈 스트링
	 * page: pageNavigator를 위한 page수
	 * 
	 * 반환
	 * navi: pageNavigator에 쓰일 변수들
	 * estimateList: 견적청구서 목록
	 */
	@Override
	public ArrayList<ApprovedListDTO> getApprovedList(Model model, UserInformDTO user, String option, int page, int countPerPage) {
		UserInformWithOptionDTO userInformWithOption = new UserInformWithOptionDTO();
		userInformWithOption.setAuth(user.getAuth());
		userInformWithOption.setUserNum(user.getUserNum());
		userInformWithOption.setOption(option);
		int total = workflowDao.getTotalApprovedSheet(userInformWithOption);
		System.out.println("total:" + total);
		PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, total);
		ArrayList<ApprovedListDTO> approvedList = workflowDao.getApprovedList(navi.getStartRecord(), navi.getCountPerPage(), userInformWithOption);

		model.addAttribute("pn", navi);
		
		return approvedList;
	}

}
