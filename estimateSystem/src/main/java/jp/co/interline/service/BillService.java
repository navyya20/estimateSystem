package jp.co.interline.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.billSystem.BillCommonDTO;

public interface BillService {

	String getDocoumentNum();

	ArrayList<EstimateListDTO> getBillList(Model model, UserInformDTO user, String option, int page, int countPerPage);

	String returnFileName(ArrayList<SystemDTO> systems, String category);

	<T extends BillCommonDTO> HashMap<String, String> insertDocument(UserInformDTO user, HttpSession session, Model model, String jsonStr, Class<T> type);

	<T extends BillCommonDTO> HashMap<String, String> updateDocument(UserInformDTO user, HttpSession session, Model model, String jsonStr, Class<T> type);


}
