package jp.co.interline.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;

import jp.co.interline.dto.ApprovedListDTO;
import jp.co.interline.dto.DocumentMasterDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformWithOptionDTO;
import jp.co.interline.dto.WorkflowDTO;
import jp.co.interline.dto.WorkflowInformDTO;




public interface WorkflowMapper {

	WorkflowInformDTO getWorkflowInformBySystemNum(int systemNum);

	int updateWorkflowInform(WorkflowInformDTO workflowInform);

	int setWorkflowInformTargetKey(WorkflowInformDTO workflowInform);

	WorkflowInformDTO getWorkflowInformWithNameBySystemNum(int systemNum);

	String getState(String documentNum);

	int insertWorkflow(WorkflowDTO workflow);

	WorkflowDTO getWorkflowByWorkflowNum(int workflowNum);

	int renewWorkflow(WorkflowDTO workflow);

	int updateState(WorkflowDTO workflow);

	int updateWorkflowNum(WorkflowDTO document);

	int getNumberOfWorkflows(int userNum);

	ArrayList<WorkflowDTO> getWorkflowWaitingList(int userNum);

	WorkflowDTO getWorkflowByDocumentNum(String documentNum);

	int updateApprove(WorkflowDTO workflow);

	int deleteWorkflow(int workflowNum);

	int stampConfirm(SystemDTO system);

	String getSystemBySystemNum(int systemNum);

	int setfileName(DocumentMasterDTO documentMaster);

	int getTotalApprovedSheet(UserInformWithOptionDTO userInformWithOption);

	ArrayList<ApprovedListDTO> getApprovedList(RowBounds rbs, UserInformWithOptionDTO userInformWithOption);

}
