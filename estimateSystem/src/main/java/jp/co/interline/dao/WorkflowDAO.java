package jp.co.interline.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.interline.dto.ApprovedListDTO;
import jp.co.interline.dto.DocumentMasterDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformWithOptionDTO;
import jp.co.interline.dto.WorkflowDTO;
import jp.co.interline.dto.WorkflowInformDTO;


@Repository
public class WorkflowDAO {
	@Autowired
	SqlSession sqlsession;

	public WorkflowInformDTO getWorkflowInformBySystemNum(int systemNum) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		WorkflowInformDTO workflowInform = mapper.getWorkflowInformBySystemNum(systemNum);
		return workflowInform;
	}
	public WorkflowInformDTO getWorkflowInformWithNameBySystemNum(int systemNum) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		WorkflowInformDTO workflowInform = mapper.getWorkflowInformWithNameBySystemNum(systemNum);
		return workflowInform;
	}
	

	public int updateWorkflowInform(WorkflowInformDTO workflowInform) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		int result = mapper.updateWorkflowInform(workflowInform);
		return result;
	}

	public int setWorkflowInformTargetKey(WorkflowInformDTO workflowInform) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		int result = mapper.setWorkflowInformTargetKey(workflowInform);
		return result;
	}
	public String getState(String documentNum) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		String state = mapper.getState(documentNum);
		return state;
	}
	public int insertWorkflow(WorkflowDTO workflow) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		//WorkflowDTO w = mapper.insertWorkflow(workflow);
		int w = mapper.insertWorkflow(workflow);
		
		return workflow.getWorkflowNum();
	}
	
	public WorkflowDTO getWorkflowByWorkflowNum(int workflowNum) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		WorkflowDTO workflow = mapper.getWorkflowByWorkflowNum(workflowNum);
		return workflow;
	}
	public int renewWorkflow(WorkflowDTO workflow) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		int result = mapper.renewWorkflow(workflow);
		return result;
	}
	public int updateState(WorkflowDTO workflow) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		int result = mapper.updateState(workflow);
		return result;
	}
	public int updateWorkflowNum(WorkflowDTO document) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		int result = mapper.updateWorkflowNum(document);
		return result;
	}
	public int getNumberOfWorkflows(int userNum) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		int result = mapper.getNumberOfWorkflows(userNum);
		return result;
	}
	public ArrayList<WorkflowDTO> getWorkflowWaitingList(int userNum) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		ArrayList<WorkflowDTO> workflowList = mapper.getWorkflowWaitingList(userNum);
		return workflowList;
	}
	public WorkflowDTO getWorkflowByDocumentNum(String documentNum) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		WorkflowDTO workflow = mapper.getWorkflowByDocumentNum(documentNum);
		return workflow;
	}
	public int updateApprove(WorkflowDTO workflow) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		int result = mapper.updateApprove(workflow);
		return result;
	}
	public int deleteWorkflow(int workflowNum) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		int result = mapper.deleteWorkflow(workflowNum);
		return result;
	}
	public int stampConfirm(SystemDTO system) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		int result = mapper.stampConfirm(system);
		return result;
	}
	public String getSystemBySystemNum(int systemNum) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		String result = mapper.getSystemBySystemNum(systemNum);
		return result;
	}
	public int setfileName(DocumentMasterDTO documentMaster) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		int result = mapper.setfileName(documentMaster);
		return result;
	}
	public int getTotalApprovedSheet(HashMap<String,String> param) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		int total = mapper.getTotalApprovedSheet(param);
		return total;
	}
	public ArrayList<ApprovedListDTO> getApprovedList(int st, int ctt, HashMap<String,String> param) {
		WorkflowMapper mapper = sqlsession.getMapper(WorkflowMapper.class);
		RowBounds rbs = new RowBounds(st,ctt);
		ArrayList<ApprovedListDTO> approvedList = mapper.getApprovedList(rbs, param);
		return approvedList;
	}

}
