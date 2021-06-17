package jp.co.interline.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.interline.dao.WorkflowDAO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.WorkflowDTO;
import jp.co.interline.dto.WorkflowInformDTO;

@Service
public class WorkflowServiceImpl implements WorkflowService {
	@Autowired
	WorkflowDAO workflowDao;

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
	@Override
	public int insertWorkflow(WorkflowInformDTO workflowInform, int documentTypeNum, String documentTypeName, String documentNum, UserInformDTO user) {
		WorkflowDTO workflow = new WorkflowDTO();
		
		workflow.setSystemNum(workflowInform.getSystemNum());
		workflow.setUserNum(user.getUserNum());
		workflow.setDocumentTypeNum(documentTypeNum);
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
			w.setPresentApprover(-1);
			w.setPresentApproverNum(-1);
		}
		
		String targetValue = as1+as2+as3;
		w.setTargetValue(targetValue);
		w.setWorkflowNum(workflowNum);
		w.setUpdater(user.getUserNum());
		int result = workflowDao.renewWorkflow(w);
		
		
		return result;
	}
	@Override
	public Boolean confirmation(int workflowNum) {
		WorkflowDTO w = workflowDao.getWorkflowByWorkflowNum(workflowNum);
		
		return w.getTargetKey().equals(w.getTargetValue());
	}
	@Override
	public int updateState(WorkflowDTO workflow) {
		int result = workflowDao.updateState(workflow);
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

	
	
	
}
