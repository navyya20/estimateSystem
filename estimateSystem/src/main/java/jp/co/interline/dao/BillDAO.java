package jp.co.interline.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.interline.dto.BillSheet1DTO;
import jp.co.interline.dto.BillSheet1ItemsRecieveDTO;
import jp.co.interline.dto.BillSiDTO;
import jp.co.interline.dto.BillSiItemsRecieveDTO;
import jp.co.interline.dto.BillSolutionDTO;
import jp.co.interline.dto.BillSolutionItemsRecieveDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.EstimateSheet1DTO;
import jp.co.interline.dto.EstimateSheet1ItemsRecieveDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformWithOptionDTO;
import jp.co.interline.dto.WorkflowInformDTO;


@Repository
public class BillDAO {
	@Autowired
	SqlSession sqlsession;

	/*
	 * public SystemDTO getBillType(int documentTypeNum) { BillMapper mapper =
	 * sqlsession.getMapper(BillMapper.class); SystemDTO system =
	 * mapper.getBillType(documentTypeNum); return system; }
	 * 
	 * public SystemDTO getBillTypeName(int documentTypeNum) { BillMapper mapper =
	 * sqlsession.getMapper(BillMapper.class); SystemDTO system =
	 * mapper.getBillTypeName(documentTypeNum); return system; }
	 */
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
	
//////////////////////////billSheet1//////////////////////////////////
	public int insertBillSheet1(BillSheet1DTO billSheet1) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		int result = mapper.insertBillSheet1(billSheet1);
		return result;
	}

	public int insertBillSheet1Items(BillSheet1ItemsRecieveDTO billSheet1ItemsReciever) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		int result = mapper.insertBillSheet1Items(billSheet1ItemsReciever);
		return result;
	}
	public int updateBillSheet1(BillSheet1DTO billSheet1) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		int result = mapper.updateBillSheet1(billSheet1);
		return result;
	}

	public int updateBillSheet1Items(BillSheet1ItemsRecieveDTO billSheet1ItemsReciever) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		int result = mapper.updateBillSheet1Items(billSheet1ItemsReciever);
		return result;
	}

	
///////////////////////////////////////////billSolution//////////////////////////////////////////	
	
	public int insertBillSolution(BillSolutionDTO billSolution) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		int result = mapper.insertBillSolution(billSolution);
		return result;
	}

	public int insertBillSolutionItems(BillSolutionItemsRecieveDTO billSolutionItemsReciever) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		int result = mapper.insertBillSolutionItems(billSolutionItemsReciever);
		return result;
	}
	
	public int updateBillSolution(BillSolutionDTO billSolution) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		int result = mapper.updateBillSolution(billSolution);
		return result;
	}

	public int updateBillSolutionItems(BillSolutionItemsRecieveDTO billSolutionItemsReciever) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		int result = mapper.updateBillSolutionItems(billSolutionItemsReciever);
		return result;
	}

	

///////////////////////////////////////////billSi//////////////////////////////////////////	
	
	public int insertBillSi(BillSiDTO billSi) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		int result = mapper.insertBillSi(billSi);
		return result;
	}
	
	public int insertBillSiItems(BillSiItemsRecieveDTO billSiItemsReciever) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		int result = mapper.insertBillSiItems(billSiItemsReciever);
		return result;
	}
	
	public int updateBillSi(BillSiDTO billSi) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		int result = mapper.updateBillSi(billSi);
		return result;
	}
	
	public int updateBillSiItems(BillSiItemsRecieveDTO billSiItemsReciever) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		int result = mapper.updateBillSiItems(billSiItemsReciever);
		return result;
	}
	
	
	

	
}
