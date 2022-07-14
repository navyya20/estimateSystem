package jp.co.interline.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import jp.co.interline.dto.DocumentTypeDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.billSystem.BillCommonDTO;
import jp.co.interline.dto.billSystem.monthlyBillTotal.MonthlyBillTotalAjaxDTO;
import jp.co.interline.dto.billSystem.monthlyBillTotal.MonthlyBillTotalAjaxForBillSiDTO;

public interface BillService {

	String getDocoumentNum();

	ArrayList<EstimateListDTO> getBillList(Model model, UserInformDTO user, String option, int page, int countPerPage);

	ArrayList<DocumentTypeDTO> getBillTypeList();
	
	ArrayList<MonthlyBillTotalAjaxDTO> getMonthlyBillTotalList(String billType, int year, int month, int userNum, String auth, String order);
	
	ArrayList<MonthlyBillTotalAjaxForBillSiDTO> getMonthlyBillSiTotalList(int year, int month, int userNum, String auth, String order);
	
	<T extends BillCommonDTO> HashMap<String, String> insertDocument(UserInformDTO user, HttpSession session, Model model, String jsonStr, Class<T> type);

	<T extends BillCommonDTO> HashMap<String, String> updateDocument(UserInformDTO user, HttpSession session, Model model, String jsonStr, Class<T> type);



}
