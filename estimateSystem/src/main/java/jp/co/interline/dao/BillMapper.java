package jp.co.interline.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;

import jp.co.interline.dto.BillCDTO;
import jp.co.interline.dto.BillCItemsRecieveDTO;
import jp.co.interline.dto.BillDDTO;
import jp.co.interline.dto.BillSheet1DTO;
import jp.co.interline.dto.BillSheet1ItemsRecieveDTO;
import jp.co.interline.dto.BillSiDTO;
import jp.co.interline.dto.BillSiItemsRecieveDTO;
import jp.co.interline.dto.BillSolutionDTO;
import jp.co.interline.dto.BillSolutionItemsRecieveDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformWithOptionDTO;




public interface BillMapper {
	//SystemDTO getBillType(int documentTypeNum);

	//SystemDTO getBillTypeName(int documentTypeNum);
	
	int getTotalBillSheet(UserInformWithOptionDTO userInformWithOption);

	ArrayList<EstimateListDTO> getBillList(RowBounds rbs, UserInformWithOptionDTO userInformWithOption);

	
	int insertBillSheet1(BillSheet1DTO estimateSheet1);

	int insertBillSheet1Items(BillSheet1ItemsRecieveDTO estimateSheet1ItemsReciever);

	int updateBillSheet1(BillSheet1DTO billSheet1);

	int updateBillSheet1Items(BillSheet1ItemsRecieveDTO billSheet1ItemsReciever);

	
	int insertBillSolution(BillSolutionDTO billSolution);

	int insertBillSolutionItems(BillSolutionItemsRecieveDTO billSolutionItemsReciever);

	int updateBillSolution(BillSolutionDTO billSolution);

	int updateBillSolutionItems(BillSolutionItemsRecieveDTO billSolutionItemsReciever);

	int insertBillSi(BillSiDTO billSi);

	int insertBillSiItems(BillSiItemsRecieveDTO billSiItemsReciever);

	int updateBillSi(BillSiDTO billSi);

	int updateBillSiItems(BillSiItemsRecieveDTO billSiItemsReciever);
	
	int insertBillC(BillCDTO billC);

	int insertBillCItems(BillCItemsRecieveDTO billCItemsReciever);

	int updateBillC(BillCDTO billC);

	int updateBillCItems(BillCItemsRecieveDTO billCItemsReciever);

	int insertBillD(BillDDTO billD);

	int updateBillD(BillDDTO billD);


}
