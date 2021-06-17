package jp.co.interline.dao;

import java.util.ArrayList;

import jp.co.interline.dto.BillSheet1DTO;
import jp.co.interline.dto.BillSheet1ItemsRecieveDTO;
import jp.co.interline.dto.SystemDTO;




public interface BillMapper {

	int insertBillSheet1(BillSheet1DTO estimateSheet1);

	int insertBillSheet1Items(BillSheet1ItemsRecieveDTO estimateSheet1ItemsReciever);

	SystemDTO getBillType(int documentTypeNum);

	SystemDTO getBillTypeName(int documentTypeNum);

	int updateBillSheet1(BillSheet1DTO billSheet1);

	int updateBillSheet1Items(BillSheet1ItemsRecieveDTO billSheet1ItemsReciever);

	BillSheet1DTO getBillSheet1ByDocumentNum(String documentNum);


}
