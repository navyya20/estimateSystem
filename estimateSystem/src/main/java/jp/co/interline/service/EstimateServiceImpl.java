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

import jp.co.interline.dao.EstimateDAO;
import jp.co.interline.dao.SystemDAO;
import jp.co.interline.dao.WorkflowDAO;
import jp.co.interline.dto.DocumentMasterDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.UserInformWithOptionDTO;
import jp.co.interline.dto.billSystem.BillCommonDTO;
import jp.co.interline.dto.estimateSystem.EstimateCommonDTO;
import jp.co.interline.dto.estimateSystem.estimateLanguage.EstimateLanguageDTO;
import jp.co.interline.dto.estimateSystem.estimateLanguage.EstimateLanguageItemDTO;
import jp.co.interline.dto.estimateSystem.estimateSi.EstimateSiDTO;
import jp.co.interline.dto.estimateSystem.estimateSi.EstimateSiItemDTO;
import jp.co.interline.dto.estimateSystem.estimateSolution.EstimateSolutionDTO;
import jp.co.interline.dto.estimateSystem.estimateSolution.EstimateSolutionItemDTO;

@Service
public class EstimateServiceImpl implements EstimateService {
	@Autowired
	WorkflowDAO workflowDao;
	
	@Autowired
	EstimateDAO estimateDao;
	
	@Autowired
	SystemDAO systemDao;
	
	//private static final int countPerPage=20;	
	private static final int pagePerGroup=10;
	
	@Override
	public String getDocoumentNum() {
		SystemDTO system = systemDao.getNumber("estimateSeq");
		int number = system.getSeqNum();
		SimpleDateFormat format = new SimpleDateFormat ( "yyMMdd");
		Date time = new Date();
		String time1 = format.format(time);
		String documentNum = "IN01-"+ time1 + "-" + String.format("%02d", number);
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
	public ArrayList<EstimateListDTO> getEstimateList(Model model, UserInformDTO user, String option, int page, int countPerPage) {
		UserInformWithOptionDTO userInformWithOption = new UserInformWithOptionDTO();
		userInformWithOption.setAuth(user.getAuth());
		userInformWithOption.setUserNum(user.getUserNum());
		userInformWithOption.setOption(option);
		int total = estimateDao.getTotalEstimateSheet(userInformWithOption);
		System.out.println("total:" + total);
		PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, total);
		ArrayList<EstimateListDTO> estimateList = estimateDao.getEstimateList(navi.getStartRecord(), navi.getCountPerPage(), userInformWithOption);

		model.addAttribute("pn", navi);
		
		return estimateList;
	}
	
	//문서번호로 해당하는 document마스터정보를 가져온다.
	@Override
	public DocumentMasterDTO getDocumentMaster(String documentNum) {
		DocumentMasterDTO document = estimateDao.getDocumentMaster(documentNum);
		return document;
	}
	
	//문서의내용을 가져온다.
	//서식타입이 지정되어있지않아 어떤 Table일지모르고 따라서 DTO도 지정되어있지않아 Hashmap으로 받아야하는경우 사용하는 서비스.
	//DocumentMasterDTO에는 문서번호와 문서타입정보가 있어야한다.
	//아이템내용은 따로 가져와야한다. 문서는 단일행, 아이템은 복수행이기때문에.
	@Override
	public HashMap<String, String> getDocumentToHashMap(DocumentMasterDTO document) {
		HashMap<String, String> contents = estimateDao.getDocumentToHashMap(document);
		return contents;
	}
	//아이템의 내용을 가져온다.
	//서식타입이 지정되어있지않아 어떤 Table일지모르고 따라서 DTO도 지정되어있지않아 Hashmap으로 받아야하는경우 사용하는 서비스.
	//DocumentMasterDTO에는 문서번호와 문서타입정보가 있어야한다.
	@Override
	public ArrayList<HashMap<String, Object>> getItemsToList(DocumentMasterDTO document) {
		ArrayList<HashMap<String, Object>> items = estimateDao.getItemsToList(document);
		return items;
	}
	
	//아이템list를 map화 해주고 contents에 추가해주는 작업. (작성ozr화면에서는 key값이 item항목명+index로 동작하기때문에.)
	//추가로 기존 유저명 유저번호 등의 고유정보를 삭제한다.
	@Override
	public void makeHashMap(HashMap<String, String> contents, ArrayList<HashMap<String, Object>> items) {
		for(int i=0 ; i < items.size() ; i++) {
			HashMap<String, Object> itemsMap = items.get(i);
			for(String strKey : itemsMap.keySet()) {
				contents.put(strKey+(i+1), itemsMap.get(strKey).toString());
			}
		}
		
		contents.remove("userNum");
		contents.remove("userName");
		contents.remove("userDepartment");
		contents.remove("userPosition");
	}
	
	@Override
	public int deleteSheet(String documentNum) {
		SystemDTO system = new SystemDTO();
		
		//문서에 연결된 청구서가 있을경우 청구서부터 지움.
		String billNum=estimateDao.getBillNum(documentNum);
		if (billNum!=null) {
			system.setDocumentNum(billNum);
			estimateDao.deleteSheet(system);
		}
		
		system.setDocumentNum(documentNum);
		int result = estimateDao.deleteSheet(system);
		return result;
	}

	@Override
	public SystemDTO getEstimate(SystemDTO system) {
		SystemDTO sys = estimateDao.getEstimate(system);
		return sys;
	}
	
	@Override
	public String returnFileName(ArrayList<SystemDTO> systems, String category) {
		SystemDTO sys=null;
		for (int i = 0; i < systems.size(); i++) {
			sys = systems.get(i);
			if (sys.getCategory().equals(category)) {
				return sys.getFileName();
			}
		}
		return "";
	}
//-------------------------------------------------------------------------------------------------------	
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@Override
	public <T extends EstimateCommonDTO> HashMap<String, String> insertDocument(UserInformDTO user, HttpSession session, Model model, String jsonStr, Class<T> type) {
		T documentDTO = jacksonUtility.readValue(jsonStr, type);
		//채번
		String documentNum = this.getDocoumentNum();
		//기본정보등록
		documentDTO.setDocumentNum(documentNum);
		documentDTO.setUpdater(user.getUserNum());
		
		HashMap<String, String> result = new HashMap<String, String>();
		EstimateCommonDTO masterInform = (EstimateCommonDTO)documentDTO;
		int result0 = estimateDao.insertMasterInform(masterInform);
		if(result0 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "請求書登録エラー");
			return result;
		}
		int result1 = estimateDao.insertDocument(documentDTO);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "見積基本情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", documentNum);
		return result;
	}
	
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@Override
	public <T extends EstimateCommonDTO> HashMap<String, String> updateDocument(UserInformDTO user, HttpSession session, Model model, String jsonStr, Class<T> type) {
		T documentDTO = jacksonUtility.readValue(jsonStr, type);
		HashMap<String, String> result = new HashMap<String, String>();
		EstimateCommonDTO masterInform = (EstimateCommonDTO)documentDTO;
		int result0 = estimateDao.updateMasterInform(masterInform);
		if(result0 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "請求書登録情報修正エラー");
			return result;
		}
		int result1 = estimateDao.updateDocument(documentDTO);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "見積基本情報格納エラー");
			return result;
		}
		result.put("errorFlag", "0");
		result.put("documentNum", documentDTO.getDocumentNum());
		return result;
	}

	
	@Override
	public void test(String s) {
		estimateDao.test(s);
		//estimateDao.test2(s);
	}
	@Override
	public void test2(String s) {
		estimateDao.test2(s);
	}

}
