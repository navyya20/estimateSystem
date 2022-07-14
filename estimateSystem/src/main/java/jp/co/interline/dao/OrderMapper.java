package jp.co.interline.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;

import jp.co.interline.dto.DocumentTypeDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.UserInformWithOptionDTO;
import jp.co.interline.dto.orderSystem.OrderCommonDTO;
import jp.co.interline.dto.orderSystem.orderA.OrderADTO;
import jp.co.interline.dto.orderSystem.orderA.OrderAItemDTO;




public interface OrderMapper {
	
	int getTotalOrderSheet(UserInformWithOptionDTO userInformWithOption);

	ArrayList<EstimateListDTO> getOrderList(RowBounds rbs, UserInformWithOptionDTO userInformWithOption);
	
	ArrayList<DocumentTypeDTO> getOrderTypeList();
	
	int insertMasterInform(OrderCommonDTO masterInform);
	
	int updateMasterInform(OrderCommonDTO masterInform);

	int insertOrderA(OrderADTO orderA);

	int updateOrderA(OrderADTO orderA);
	
}
