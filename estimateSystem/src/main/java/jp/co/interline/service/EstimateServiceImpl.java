package jp.co.interline.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.co.interline.dao.EstimateDAO;
import jp.co.interline.dao.SystemDAO;
import jp.co.interline.dao.WorkflowDAO;
import jp.co.interline.dto.DocumentMasterDTO;
import jp.co.interline.dto.EstimateLanguageDTO;
import jp.co.interline.dto.EstimateLanguageItemsRecieveDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.EstimateSheet1DTO;
import jp.co.interline.dto.EstimateSheet1ItemsRecieveDTO;
import jp.co.interline.dto.EstimateSiDTO;
import jp.co.interline.dto.EstimateSiItemsRecieveDTO;
import jp.co.interline.dto.EstimateSolutionDTO;
import jp.co.interline.dto.EstimateSolutionItemsRecieveDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.UserInformWithOptionDTO;
import jp.co.interline.dto.testDTO;

@Service
public class EstimateServiceImpl implements EstimateService {
	@Autowired
	WorkflowDAO workflowDao;
	
	@Autowired
	EstimateDAO estimateDao;
	
	@Autowired
	SystemDAO systemDao;
	
	private static final int countPerPage=20;	
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

	@Override
	public int insertEstimateSheet1(EstimateSheet1DTO estimateSheet1) {
		ArrayList<SystemDTO> system = systemDao.getFileNames();
		String stampFileName=returnFileName(system, "stamp");
		String logoFileName=returnFileName(system, "logo");
		estimateSheet1.setStampFileName(stampFileName);
		estimateSheet1.setLogoFileName(logoFileName);
		int result = estimateDao.insertEstimateSheet1(estimateSheet1);
		return result;
	}

	@Override
	public int insertEstimateSheet1Items(EstimateSheet1ItemsRecieveDTO estimateSheet1ItemsReciever) {
		
		int result = estimateDao.insertEstimateSheet1Items(estimateSheet1ItemsReciever);
		return result;
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
	public ArrayList<EstimateListDTO> getEstimateList(Model model, UserInformDTO user, String option, int page) {
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
	
	//---------------------------------------estimateSheet1-----------------------

	@Override
	public int updateEstimateSheet1(EstimateSheet1DTO estimateSheet1) {
		ArrayList<SystemDTO> system = systemDao.getFileNames();
		String stampFileName=returnFileName(system, "stamp");
		String logoFileName=returnFileName(system, "logo");
		estimateSheet1.setStampFileName(stampFileName);
		estimateSheet1.setLogoFileName(logoFileName);
		int result = estimateDao.updateEstimateSheet1(estimateSheet1);
		return result;
	}

	@Override
	public int updateEstimateSheet1Items(EstimateSheet1ItemsRecieveDTO estimateSheet1ItemsReciever) {
		int result = estimateDao.updateEstimateSheet1Items(estimateSheet1ItemsReciever);
		return result;
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
	public void test() {
		testDTO test = new testDTO();
		ArrayList<HashMap<String, String>> h = test.getHashMapList();
		estimateDao.test(h);
		
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

/////////////////////////////////EstimateSolution//////////////////////////////////////////////////
	@Override
	public int insertEstimateSolution(EstimateSolutionDTO estimateSolution) {
		ArrayList<SystemDTO> system = systemDao.getFileNames();
		String stampFileName=returnFileName(system, "stamp");
		String logoFileName=returnFileName(system, "logo");
		estimateSolution.setStampFileName(stampFileName);
		estimateSolution.setLogoFileName(logoFileName);
		int result = estimateDao.insertEstimateSolution(estimateSolution);
		return result;
	}

	@Override
	public int insertEstimateSolutionItems(EstimateSolutionItemsRecieveDTO estimateSolutionItemsReciever) {
		int result = estimateDao.insertEstimateSolutionItems(estimateSolutionItemsReciever);
		return result;
	}

	@Override
	public int updateEstimateSolution(EstimateSolutionDTO estimateSolution) {
		ArrayList<SystemDTO> system = systemDao.getFileNames();
		String stampFileName=returnFileName(system, "stamp");
		String logoFileName=returnFileName(system, "logo");
		estimateSolution.setStampFileName(stampFileName);
		estimateSolution.setLogoFileName(logoFileName);
		int result = estimateDao.updateEstimateSolution(estimateSolution);
		return result;
	}

	@Override
	public int updateEstimateSolutionItems(EstimateSolutionItemsRecieveDTO estimateSolutionItemsReciever) {
		int result = estimateDao.updateEstimateSolutionItems(estimateSolutionItemsReciever);
		return result;
	}

//-------------------------------------------------------------------------------------------------------	

	
	
	
/////////////////////////////////EstimateLanguage//////////////////////////////////////////////////
	@Override
	public int insertEstimateLanguage(EstimateLanguageDTO estimateLanguage) {
		ArrayList<SystemDTO> system = systemDao.getFileNames();
		String stampFileName=returnFileName(system, "stamp");
		String logoFileName=returnFileName(system, "logo");
		estimateLanguage.setStampFileName(stampFileName);
		estimateLanguage.setLogoFileName(logoFileName);
		int result = estimateDao.insertEstimateLanguage(estimateLanguage);
		return result;
	}

	@Override
	public int insertEstimateLanguageItems(EstimateLanguageItemsRecieveDTO estimateLanguageItemsReciever) {
		int result = estimateDao.insertEstimateLanguageItems(estimateLanguageItemsReciever);
		return result;
	}

	@Override
	public int updateEstimateLanguage(EstimateLanguageDTO estimateLanguage) {
		ArrayList<SystemDTO> system = systemDao.getFileNames();
		String stampFileName=returnFileName(system, "stamp");
		String logoFileName=returnFileName(system, "logo");
		estimateLanguage.setStampFileName(stampFileName);
		estimateLanguage.setLogoFileName(logoFileName);
		int result = estimateDao.updateEstimateLanguage(estimateLanguage);
		return result;
	}

	@Override
	public int updateEstimateLanguageItems(EstimateLanguageItemsRecieveDTO estimateLanguageItemsReciever) {
		int result = estimateDao.updateEstimateLanguageItems(estimateLanguageItemsReciever);
		return result;
	}
//-------------------------------------------------------------------------------------------------------	

	
	
	
	
/////////////////////////////////EstimateSi//////////////////////////////////////////////////
	@Override
	public int insertEstimateSi(EstimateSiDTO estimateSi) {
		ArrayList<SystemDTO> system = systemDao.getFileNames();
		String stampFileName=returnFileName(system, "stamp");
		String logoFileName=returnFileName(system, "logo");
		estimateSi.setStampFileName(stampFileName);
		estimateSi.setLogoFileName(logoFileName);
		int result = estimateDao.insertEstimateSi(estimateSi);
		return result;
	}

	@Override
	public int insertEstimateSiItems(EstimateSiItemsRecieveDTO estimateSiItemsReciever) {
		int result = estimateDao.insertEstimateSiItems(estimateSiItemsReciever);
		return result;
	}

	@Override
	public int updateEstimateSi(EstimateSiDTO estimateSi) {
		ArrayList<SystemDTO> system = systemDao.getFileNames();
		String stampFileName=returnFileName(system, "stamp");
		String logoFileName=returnFileName(system, "logo");
		estimateSi.setStampFileName(stampFileName);
		estimateSi.setLogoFileName(logoFileName);
		int result = estimateDao.updateEstimateSi(estimateSi);
		return result;
	}

	@Override
	public int updateEstimateSiItems(EstimateSiItemsRecieveDTO estimateSiItemsReciever) {
		int result = estimateDao.updateEstimateSiItems(estimateSiItemsReciever);
		return result;
	}
//-------------------------------------------------------------------------------------------------------	


	

	
	

}
