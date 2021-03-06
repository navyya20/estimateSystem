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

import jp.co.interline.dao.BillDAO;
import jp.co.interline.dao.SystemDAO;
import jp.co.interline.dao.WorkflowDAO;
import jp.co.interline.dto.DocumentTypeDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.UserInformWithOptionDTO;
import jp.co.interline.dto.billSystem.BillCommonDTO;
import jp.co.interline.dto.billSystem.monthlyBillTotal.MonthlyBillTotalAjaxDTO;
import jp.co.interline.dto.billSystem.monthlyBillTotal.MonthlyBillTotalAjaxForBillSiDTO;

@Service
public class BillServiceImpl<T> implements BillService {
	@Autowired
	WorkflowDAO workflowDao;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	BillDAO billDao;
	
	@Autowired
	SystemDAO systemDao;
	
	//private static final int countPerPage=20;	
	private static final int pagePerGroup=10;
	
	@Override
	public String getDocoumentNum() {
		SystemDTO system = systemDao.getNumber("billSeq");
		int number = system.getSeqNum();
		SimpleDateFormat format = new SimpleDateFormat ( "yyMMdd");
		Date time = new Date();
		String time1 = format.format(time);
		String documentNum = "IN02-"+ time1 + "-" + String.format("%02d", number);
		//IN01-210505-01
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
	public ArrayList<EstimateListDTO> getBillList(Model model, UserInformDTO user, String option, int page, int countPerPage) {
		UserInformWithOptionDTO userInformWithOption = new UserInformWithOptionDTO();
		userInformWithOption.setAuth(user.getAuth());
		userInformWithOption.setUserNum(user.getUserNum());
		userInformWithOption.setOption(option);
		int total = billDao.getTotalBillSheet(userInformWithOption);
		System.out.println("total:" + total);
		PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, total);
		ArrayList<EstimateListDTO> billList = billDao.getBillList(navi.getStartRecord(), navi.getCountPerPage(), userInformWithOption);

		model.addAttribute("pn", navi);
		
		return billList;
	}
	
	/*
	 * documentType테이블에서 sysnum=2(청구서)인 데이터를 모두 가져온다. 순서는  orderNum의 오름차순으로
	 */
	@Override
	public ArrayList<DocumentTypeDTO> getBillTypeList() {
		ArrayList<DocumentTypeDTO> typeList = billDao.getBillTypeList();
		return typeList;
	}
	/*
	 * billTotalView뷰에서 청구서타입,해당 년,월에 해당하는 청구서 목록을 가져온다.
	 */
	@Override
	public ArrayList<MonthlyBillTotalAjaxDTO> getMonthlyBillTotalList(String billType, int year, int month, int userNum, String auth, String order) {
		String start = year + "-" + month + "-" + "1";
		if(month == 12) {
			year++;
			month = 0;
		}
		String end = year + "-" + (month+1) + "-" + "1";
		ArrayList<MonthlyBillTotalAjaxDTO> monthlyBillTotalList = billDao.getMonthlyBillTotalList(billType, start, end, userNum, auth, order);
		return monthlyBillTotalList;
	}
	/*
	 * billTotalView뷰에서 Si만을 위한 별도 데이터불러오기.해당 년,월에 해당하는 Si청구서 목록을 가져온다.
	 */
	@Override
	public ArrayList<MonthlyBillTotalAjaxForBillSiDTO> getMonthlyBillSiTotalList(int year, int month, int userNum, String auth, String order) {
		String start = year + "-" + month + "-" + "1";
		if(month == 12) {
			year++;
			month = 0;
		}
		String end = year + "-" + (month+1) + "-" + "1";
		ArrayList<MonthlyBillTotalAjaxForBillSiDTO> billList = billDao.getMonthlyBillSiTotalList(start, end, userNum, auth, order);
		return billList;
	}
////////////////////////////////////////////////
	@SuppressWarnings("hiding")
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	public <T extends BillCommonDTO> HashMap<String, String> insertDocument(UserInformDTO user, HttpSession session, Model model, String jsonStr,Class<T> type) {
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
		BillCommonDTO masterInform = (BillCommonDTO)documentDTO;
		int result0 = billDao.insertMasterInform(masterInform);
		if(result0 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "請求書登録エラー");
			return result;
		}
		int result1 = billDao.insertDocument(documentDTO);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "請求情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", documentNum);
		return result;
	} 
	
	@SuppressWarnings("hiding")
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	public <T extends BillCommonDTO> HashMap<String, String> updateDocument(UserInformDTO user, HttpSession session, Model model, String jsonStr,Class<T> type) {
		T documentDTO = jacksonUtility.readValue(jsonStr, type);
		documentDTO.setUpdater(user.getUserNum());
		HashMap<String, String> result = new HashMap<String, String>();
		BillCommonDTO masterInform = (BillCommonDTO)documentDTO;
		int result0 = billDao.updateMasterInform(masterInform);
		if(result0 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "請求書登録情報修正エラー");
			return result;
		}
		int result1 = billDao.updateDocument(documentDTO);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "請求情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", documentDTO.getDocumentNum());
		return result;
	}

	

	

	
}
