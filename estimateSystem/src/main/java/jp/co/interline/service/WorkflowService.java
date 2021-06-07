package jp.co.interline.service;

import java.util.ArrayList;

import jp.co.interline.dto.WorkflowInformDTO;


public interface WorkflowService {

	WorkflowInformDTO getWorkflowInformBySystemNum(int systemNum);
	WorkflowInformDTO getWorkflowInformWithNameBySystemNum(int systemNum);

	int updateWorkflowInform(int userNum, int systemNum, int order);

	int updateTarget(int systemNum);

}
