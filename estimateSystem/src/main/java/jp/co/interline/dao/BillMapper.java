package jp.co.interline.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;

import jp.co.interline.dto.DocumentTypeDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.UserInformWithOptionDTO;
import jp.co.interline.dto.billSystem.BillCommonDTO;
import jp.co.interline.dto.billSystem.billC.BillCDTO;
import jp.co.interline.dto.billSystem.billC.BillCItemDTO;
import jp.co.interline.dto.billSystem.billD.BillDDTO;
import jp.co.interline.dto.billSystem.billSi.BillSiDTO;
import jp.co.interline.dto.billSystem.billSi.BillSiItemDTO;
import jp.co.interline.dto.billSystem.billSolution.BillSolutionDTO;
import jp.co.interline.dto.billSystem.billSolution.BillSolutionItemDTO;
import jp.co.interline.dto.billSystem.monthlyBillTotal.MonthlyBillTotalAjaxDTO;
import jp.co.interline.dto.billSystem.monthlyBillTotal.MonthlyBillTotalAjaxForBillSiDTO;




public interface BillMapper {
	
	int getTotalBillSheet(UserInformWithOptionDTO userInformWithOption);

	ArrayList<EstimateListDTO> getBillList(RowBounds rbs, UserInformWithOptionDTO userInformWithOption);
	
	ArrayList<DocumentTypeDTO> getBillTypeList();
	
	ArrayList<MonthlyBillTotalAjaxDTO> getMonthlyBillTotalList(HashMap<String, String> map);
	
	ArrayList<MonthlyBillTotalAjaxForBillSiDTO> getMonthlyBillSiTotalList(HashMap<String, String> map);

	int insertMasterInform(BillCommonDTO masterInform);
	
	int updateMasterInform(BillCommonDTO masterInform);
	
	int insertBillSolution(BillSolutionDTO billSolution);

	int insertBillSolutionItems(BillSolutionItemDTO billSolutionItemsReciever);

	int updateBillSolution(BillSolutionDTO billSolution);

	int updateBillSolutionItems(BillSolutionItemDTO billSolutionItemsReciever);

	int insertBillSi(BillSiDTO billSi);

	int insertBillSiItems(BillSiItemDTO billSiItemsReciever);

	int updateBillSi(BillSiDTO billSi);

	int updateBillSiItems(BillSiItemDTO billSiItemsReciever);
	
	int insertBillC(BillCDTO billC);

	int insertBillCItems(BillCItemDTO billCItemsReciever);

	int updateBillC(BillCDTO billC);

	int updateBillCItems(BillCItemDTO billCItemsReciever);

	int insertBillD(BillDDTO billD);

	int updateBillD(BillDDTO billD);

	


}
