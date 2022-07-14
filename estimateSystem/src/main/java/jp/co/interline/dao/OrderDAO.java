package jp.co.interline.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.interline.dto.DocumentTypeDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.UserInformWithOptionDTO;
import jp.co.interline.dto.orderSystem.OrderCommonDTO;
import jp.co.interline.dto.orderSystem.orderA.OrderADTO;


@Repository
public class OrderDAO {
	@Autowired
	SqlSession sqlsession;

	public int getTotalOrderSheet(UserInformWithOptionDTO userInformWithOption) {
		OrderMapper mapper = sqlsession.getMapper(OrderMapper.class);
		int total = mapper.getTotalOrderSheet(userInformWithOption);
		return total;
	}

	public ArrayList<EstimateListDTO> getOrderList(int st, int ctt, UserInformWithOptionDTO userInformWithOption){
		OrderMapper mapper = sqlsession.getMapper(OrderMapper.class);
		RowBounds rbs = new RowBounds(st,ctt);
		ArrayList<EstimateListDTO> orderList = mapper.getOrderList(rbs, userInformWithOption);
		return orderList;
	}
	
	public ArrayList<DocumentTypeDTO> getOrderTypeList() {
		OrderMapper mapper = sqlsession.getMapper(OrderMapper.class);
		ArrayList<DocumentTypeDTO> typeList = mapper.getOrderTypeList();
		return typeList;
	}
	
//////////////////////////////////////////////////////////////////////////////
	public int insertMasterInform(OrderCommonDTO masterInform) {
		OrderMapper mapper = sqlsession.getMapper(OrderMapper.class);
		return mapper.insertMasterInform(masterInform);
	}
	public int updateMasterInform(OrderCommonDTO masterInform) {
		OrderMapper mapper = sqlsession.getMapper(OrderMapper.class);
		return mapper.updateMasterInform(masterInform);
	}
	
	public <T extends OrderCommonDTO> int insertDocument(T dto) {
		OrderMapper mapper = sqlsession.getMapper(OrderMapper.class);
		int result = 0;
		switch (dto.getDocumentTypeName()) {
		case "orderA":
			result = mapper.insertOrderA((OrderADTO)dto);
			break;
		default:
			return result;
		}
		return result;
	}

	public <T extends OrderCommonDTO> int updateDocument(T dto) {
		OrderMapper mapper = sqlsession.getMapper(OrderMapper.class);
		int result = 0;
		switch (dto.getDocumentTypeName()) {
		case "orderA":
			result = mapper.updateOrderA((OrderADTO)dto);
			break;
		default:
			return result;
		}
		return result;
	}

	



	
}
