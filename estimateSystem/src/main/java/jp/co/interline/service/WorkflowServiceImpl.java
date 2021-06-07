package jp.co.interline.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.interline.dao.BasicDAO;
import jp.co.interline.dao.WorkflowDAO;
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

	
	
	
}
