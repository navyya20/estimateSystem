package jp.co.interline.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.interline.dto.DocumentTypeDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.UserInformWithOptionDTO;
import jp.co.interline.dto.billSystem.BillCommonDTO;
import jp.co.interline.dto.billSystem.billC.BillCDTO;
import jp.co.interline.dto.billSystem.billD.BillDDTO;
import jp.co.interline.dto.billSystem.billSi.BillSiDTO;
import jp.co.interline.dto.billSystem.billSolution.BillSolutionDTO;
import jp.co.interline.dto.billSystem.monthlyBillTotal.MonthlyBillTotalAjaxDTO;
import jp.co.interline.dto.billSystem.monthlyBillTotal.MonthlyBillTotalAjaxForBillSiDTO;


@Repository
public class BillDAO {
	@Autowired
	SqlSession sqlsession;

	public int getTotalBillSheet(UserInformWithOptionDTO userInformWithOption) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		int total = mapper.getTotalBillSheet(userInformWithOption);
		return total;
	}

	public ArrayList<EstimateListDTO> getBillList(int st, int ctt, UserInformWithOptionDTO userInformWithOption){
			BillMapper mapper = sqlsession.getMapper(BillMapper.class);
			RowBounds rbs = new RowBounds(st,ctt);
			ArrayList<EstimateListDTO> billList = mapper.getBillList(rbs, userInformWithOption);
			return billList;
	}
	
	public ArrayList<DocumentTypeDTO> getBillTypeList() {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		ArrayList<DocumentTypeDTO> typeList = mapper.getBillTypeList();
		return typeList;
	}
	
	public ArrayList<MonthlyBillTotalAjaxDTO> getMonthlyBillTotalList(String billType, String start, String end, int userNum, String auth, String order) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		HashMap<String, String> map = new HashMap<>();
		map.put("billType", billType);
		map.put("start", start);
		map.put("end", end);
		map.put("userNum", userNum+"");
		map.put("auth", auth);	
		map.put("order", order);	
		ArrayList<MonthlyBillTotalAjaxDTO> monthlyBillTotalList = mapper.getMonthlyBillTotalList(map);
		return monthlyBillTotalList;
	}
	
	public ArrayList<MonthlyBillTotalAjaxForBillSiDTO> getMonthlyBillSiTotalList(String start, String end, int userNum, String auth, String order) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		HashMap<String, String> map = new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		map.put("userNum", userNum+"");
		map.put("auth", auth);	
		map.put("order", order);	
		ArrayList<MonthlyBillTotalAjaxForBillSiDTO> billList = mapper.getMonthlyBillSiTotalList(map);
		return billList;
	}

//////////////////////////////////////////////////////////////////////////////
	public int insertMasterInform(BillCommonDTO masterInform) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		return mapper.insertMasterInform(masterInform);
	}
	public int updateMasterInform(BillCommonDTO masterInform) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		return mapper.updateMasterInform(masterInform);
	}
	
	public <T extends BillCommonDTO> int insertDocument(T dto) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		int result = 0;
		switch (dto.getDocumentTypeName()) {
		case "billSolution":
			result = mapper.insertBillSolution((BillSolutionDTO)dto);
			break;
		case "billSi":
			result = mapper.insertBillSi((BillSiDTO)dto);
			break;
		case "billC":
			result = mapper.insertBillC((BillCDTO)dto);
			break;
		case "billD":
			result = mapper.insertBillD((BillDDTO)dto);
			break;
		default:
			return result;
		}
		return result;
	}

	public <T extends BillCommonDTO> int updateDocument(T dto) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		int result = 0;
		switch (dto.getDocumentTypeName()) {
		case "billSolution":
			result = mapper.updateBillSolution((BillSolutionDTO)dto);
			break;
		case "billSi":
			result = mapper.updateBillSi((BillSiDTO)dto);
			break;
		case "billC":
			result = mapper.updateBillC((BillCDTO)dto);
			break;
		case "billD":
			result = mapper.updateBillD((BillDDTO)dto);
			break;
		default:
			return result;
		}
		return result;
	}



	
}
