package jp.co.interline.service;

import java.util.ArrayList;

import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.WorkflowDTO;
import jp.co.interline.dto.WorkflowInformDTO;


public interface WorkflowService {

	WorkflowInformDTO getWorkflowInformBySystemNum(int systemNum);
	WorkflowInformDTO getWorkflowInformWithNameBySystemNum(int systemNum);

	int updateWorkflowInform(int userNum, int systemNum, int order);

	int updateTarget(int systemNum);
	String getState(String billNum);
	int insertWorkflow(WorkflowInformDTO workflowInform, String documentTypeName, String documentNum, UserInformDTO user);
	int renewWorkflow(int workflowNum, UserInformDTO user);
	Boolean confirmation(int workflowNum, String documentNum, String documentTypeName);
	int updateStateWRI(String documentNum);
	int updateWorkflowNum(WorkflowDTO document);
	int getNumberOfWorkflows(int userNum);
	ArrayList<WorkflowDTO> getWorkflowWaitingList(int userNum);
	WorkflowDTO getWorkflowByDocumentNum(String documentNum);
	int updateApprove(WorkflowDTO workflow, UserInformDTO user);
	int deleteWorkflow(int workflowNum);
	int stampConfirm(SystemDTO system);

}
