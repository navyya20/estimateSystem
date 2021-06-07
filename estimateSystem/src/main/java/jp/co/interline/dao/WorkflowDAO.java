package jp.co.interline.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.interline.dto.WorkflowInformDTO;


@Repository
public class WorkflowDAO implements WorkflowMapper {
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

	
	
	
}
