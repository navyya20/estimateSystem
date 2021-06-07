package jp.co.interline.dao;

import java.util.ArrayList;

import jp.co.interline.dto.WorkflowInformDTO;




public interface WorkflowMapper {

	WorkflowInformDTO getWorkflowInformBySystemNum(int systemNum);

	int updateWorkflowInform(WorkflowInformDTO workflowInform);

	int setWorkflowInformTargetKey(WorkflowInformDTO workflowInform);

	WorkflowInformDTO getWorkflowInformWithNameBySystemNum(int systemNum);

}
