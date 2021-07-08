package jp.co.interline.service;

import java.util.ArrayList;

import org.springframework.ui.Model;

import jp.co.interline.dto.BillSheet1DTO;
import jp.co.interline.dto.BillSheet1ItemsRecieveDTO;
import jp.co.interline.dto.BillSolutionDTO;
import jp.co.interline.dto.BillSolutionItemsRecieveDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;

public interface BillService {

	String getDocoumentNum();

	int insertBillSheet1(BillSheet1DTO billSheet1);

	int insertBillSheet1Items(BillSheet1ItemsRecieveDTO billSheet1ItemsReciever);

	//SystemDTO getBillType(int documentTypeNum);

	//SystemDTO getBillTypeName(int documentTypeNum);

	int updateBillSheet1(BillSheet1DTO billSheet1);

	int updateBillSheet1Items(BillSheet1ItemsRecieveDTO billSheet1ItemsReciever);

	ArrayList<EstimateListDTO> getBillList(Model model, UserInformDTO user, String option, int page);

	String returnFileName(ArrayList<SystemDTO> systems, String category);

	int insertBillSolution(BillSolutionDTO billSolution);

	int insertBillSolutionItems(BillSolutionItemsRecieveDTO billSolutionItemsReciever);

	int updateBillSolution(BillSolutionDTO billSolution);

	int updateBillSolutionItems(BillSolutionItemsRecieveDTO billSolutionItemsReciever);


	


}
