package jp.co.interline.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.interline.dto.BillSheet1DTO;
import jp.co.interline.dto.BillSheet1ItemsRecieveDTO;
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

	public SystemDTO getBillType(int documentTypeNum) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		SystemDTO system = mapper.getBillType(documentTypeNum);
		return system;
	}

	public SystemDTO getBillTypeName(int documentTypeNum) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		SystemDTO system = mapper.getBillTypeName(documentTypeNum);
		return system;
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

	public BillSheet1DTO getBillSheet1ByDocumentNum(String documentNum) {
		BillMapper mapper = sqlsession.getMapper(BillMapper.class);
		BillSheet1DTO billSheet1DTO = mapper.getBillSheet1ByDocumentNum(documentNum);
		return billSheet1DTO;
	}

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


	
}
