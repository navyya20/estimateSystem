package jp.co.interline.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.EstimateSheet1DTO;
import jp.co.interline.dto.EstimateSheet1ItemsRecieveDTO;
import jp.co.interline.dto.EstimateSolutionDTO;
import jp.co.interline.dto.EstimateSolutionItemsRecieveDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.UserInformWithOptionDTO;
import jp.co.interline.dto.WorkflowInformDTO;


@Repository
public class EstimateDAO {
	@Autowired
	SqlSession sqlsession;

	public int insertEstimateSheet1(EstimateSheet1DTO estimateSheet1) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.insertEstimateSheet1(estimateSheet1);
		return result;
	}

	public int insertEstimateSheet1Items(EstimateSheet1ItemsRecieveDTO estimateSheet1ItemsReciever) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.insertEstimateSheet1Items(estimateSheet1ItemsReciever);
		return result;
	}

	public int getTotalEstimateSheet(UserInformWithOptionDTO userInformWithOption) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int total = mapper.getTotalEstimateSheet(userInformWithOption);
		return total;
	}

	

	public ArrayList<EstimateListDTO> getEstimateList(int st, int ctt, UserInformWithOptionDTO userInformWithOption) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		RowBounds rbs = new RowBounds(st,ctt);
		ArrayList<EstimateListDTO> estimateList = mapper.getEstimateList(rbs, userInformWithOption);
		return estimateList;
	}

	public int updateEstimateSheet1(EstimateSheet1DTO estimateSheet1) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.updateEstimateSheet1(estimateSheet1);
		return result;
	}

	public int updateEstimateSheet1Items(EstimateSheet1ItemsRecieveDTO estimateSheet1ItemsReciever) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.updateEstimateSheet1Items(estimateSheet1ItemsReciever);
		return result;
	}

	public int deleteSheet(SystemDTO system) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.deleteSheet(system);
		return result;
	}


	public String getBillNum(String documentNum) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		String billNum = mapper.getBillNum(documentNum);
		return billNum;
	}


	public void test(ArrayList<HashMap<String, String>> arr) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		mapper.testTest(arr);

		
	}

	public int insertEstimateSolution(EstimateSolutionDTO estimateSolution) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.insertEstimateSolution(estimateSolution);
		return result;
	}

	public int insertEstimateSolutionItems(EstimateSolutionItemsRecieveDTO estimateSolutionItemsReciever) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.insertEstimateSolutionItems(estimateSolutionItemsReciever);
		return result;
	}

	public SystemDTO getEstimate(SystemDTO system) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		SystemDTO sys = mapper.getEstimate(system);
		return sys;
	}

	public int updateEstimateSolution(EstimateSolutionDTO estimateSolution) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.updateEstimateSolution(estimateSolution);
		return result;
	}

	public int updateEstimateSolutionItems(EstimateSolutionItemsRecieveDTO estimateSolutionItemsReciever) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.updateEstimateSolutionItems(estimateSolutionItemsReciever);
		return result;
	}




	//public WorkflowInformDTO getWorkflowInformBySystemNum(int systemNum) {
	//	WorkflowMapper mapper = sqlsession.getMapper(EstimateMapper.class);
	//	return null;
	//}

	
	
	
}
