package jp.co.interline.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import jp.co.interline.dto.DocumentTypeDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.orderSystem.OrderCommonDTO;

public interface OrderService {

	String getDocoumentNum();

	ArrayList<EstimateListDTO> getOrderList(Model model, UserInformDTO user, String option, int page, int countPerPage);

	ArrayList<DocumentTypeDTO> getOrderTypeList();
	
	<T extends OrderCommonDTO> HashMap<String, String> insertDocument(UserInformDTO user, HttpSession session, Model model, String jsonStr, Class<T> type);

	<T extends OrderCommonDTO> HashMap<String, String> updateDocument(UserInformDTO user, HttpSession session, Model model, String jsonStr, Class<T> type);

	



}
