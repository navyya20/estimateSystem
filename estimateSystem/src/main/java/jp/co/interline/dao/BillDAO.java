package jp.co.interline.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.interline.dto.BillSheet1DTO;
import jp.co.interline.dto.BillSheet1ItemsRecieveDTO;
import jp.co.interline.dto.EstimateSheet1DTO;
import jp.co.interline.dto.EstimateSheet1ItemsRecieveDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.WorkflowInformDTO;


@Repository
public class BillDAO implements BillMapper {
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



	
	
	
}
