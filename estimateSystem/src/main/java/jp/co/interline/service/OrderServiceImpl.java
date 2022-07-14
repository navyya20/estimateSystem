package jp.co.interline.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import jp.co.interline.dao.OrderDAO;
import jp.co.interline.dao.SystemDAO;
import jp.co.interline.dao.WorkflowDAO;
import jp.co.interline.dto.DocumentTypeDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.UserInformWithOptionDTO;
import jp.co.interline.dto.orderSystem.OrderCommonDTO;

@Service
public class OrderServiceImpl<T> implements OrderService {
	@Autowired
	WorkflowDAO workflowDao;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	OrderDAO orderDao;
	
	@Autowired
	SystemDAO systemDao;
	
	//private static final int countPerPage=20;	
	private static final int pagePerGroup=10;
	
	@Override
	public String getDocoumentNum() {
		SystemDTO system = systemDao.getNumber("orderSeq");
		int number = system.getSeqNum();
		SimpleDateFormat format = new SimpleDateFormat ( "yyMMdd");
		Date time = new Date();
		String time1 = format.format(time);
		String documentNum = "IN03-"+ time1 + "-" + String.format("%02d", number);
		//IN03-210505-01
		return documentNum;
	}
	
	/*
	 * option: order by절에 들어갈 스트링
	 * page: pageNavigator를 위한 page수
	 * 
	 * 반환
	 * navi: pageNavigator에 쓰일 변수들
	 * estimateList: 견적청구서 목록
	 */
	@Override
	public ArrayList<EstimateListDTO> getOrderList(Model model, UserInformDTO user, String option, int page, int countPerPage) {
		UserInformWithOptionDTO userInformWithOption = new UserInformWithOptionDTO();
		userInformWithOption.setAuth(user.getAuth());
		userInformWithOption.setUserNum(user.getUserNum());
		userInformWithOption.setOption(option);
		int total = orderDao.getTotalOrderSheet(userInformWithOption);
		System.out.println("total:" + total);
		PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, total);
		ArrayList<EstimateListDTO> orderList = orderDao.getOrderList(navi.getStartRecord(), navi.getCountPerPage(), userInformWithOption);
		model.addAttribute("pn", navi);
		return orderList;
	}
	
	/*
	 * documentType테이블에서 sysnum=3(주문서)인 데이터를 모두 가져온다. 순서는  orderNum의 오름차순으로
	 */
	@Override
	public ArrayList<DocumentTypeDTO> getOrderTypeList() {
		ArrayList<DocumentTypeDTO> typeList = orderDao.getOrderTypeList();
		return typeList;
	}
	
////////////////////////////////////////////////
	@SuppressWarnings("hiding")
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	public <T extends OrderCommonDTO> HashMap<String, String> insertDocument(UserInformDTO user, HttpSession session, Model model, String jsonStr,Class<T> type) {
		T documentDTO = jacksonUtility.readValue(jsonStr, type);
		//채번
		String documentNum = this.getDocoumentNum();
		//기본정보등록
		documentDTO.setDocumentNum(documentNum);
		documentDTO.setUpdater(user.getUserNum());
		HashMap<String,String> fileNamesMap = companyService.getfileNames();
		documentDTO.setStampFileName(fileNamesMap.get("stamp"));
		documentDTO.setLogoFileName(fileNamesMap.get("logo"));
		
		HashMap<String, String> result = new HashMap<String, String>();
		OrderCommonDTO masterInform = (OrderCommonDTO)documentDTO;
		int result0 = orderDao.insertMasterInform(masterInform);
		if(result0 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "注文書登録エラー");
			return result;
		}
		int result1 = orderDao.insertDocument(documentDTO);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "注文情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", documentNum);
		return result;
	} 
	
	@SuppressWarnings("hiding")
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	public <T extends OrderCommonDTO> HashMap<String, String> updateDocument(UserInformDTO user, HttpSession session, Model model, String jsonStr,Class<T> type) {
		T documentDTO = jacksonUtility.readValue(jsonStr, type);
		documentDTO.setUpdater(user.getUserNum());
		HashMap<String, String> result = new HashMap<String, String>();
		OrderCommonDTO masterInform = (OrderCommonDTO)documentDTO;
		int result0 = orderDao.updateMasterInform(masterInform);
		if(result0 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "注文書登録情報修正エラー");
			return result;
		}
		int result1 = orderDao.updateDocument(documentDTO);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "注文情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", documentDTO.getDocumentNum()+"更新エラー");
		return result;
	}

	
}
