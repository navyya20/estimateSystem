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
import jp.co.interline.dto.DocumentTypeDTO;
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
	 * option: order by?????? ????????? ?????????
	 * page: pageNavigator??? ?????? page???
	 * 
	 * ??????
	 * navi: pageNavigator??? ?????? ?????????
	 * estimateList: ??????????????? ??????
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
	
	//??????????????? ???????????? document?????????????????? ????????????.
	@Override
	public DocumentMasterDTO getDocumentMaster(String documentNum) {
		DocumentMasterDTO document = estimateDao.getDocumentMaster(documentNum);
		return document;
	}
	
	//?????????????????? ????????????.
	//??????????????? ???????????????????????? ?????? Table??????????????? ????????? DTO??? ???????????????????????? Hashmap?????? ????????????????????? ???????????? ?????????.
	//DocumentMasterDTO?????? ??????????????? ????????????????????? ???????????????.
	//?????????????????? ?????? ??????????????????. ????????? ?????????, ???????????? ????????????????????????.
	@Override
	public HashMap<String, String> getDocumentToHashMap(DocumentMasterDTO document) {
		HashMap<String, String> contents = estimateDao.getDocumentToHashMap(document);
		return contents;
	}
	//???????????? ????????? ????????????.
	//??????????????? ???????????????????????? ?????? Table??????????????? ????????? DTO??? ???????????????????????? Hashmap?????? ????????????????????? ???????????? ?????????.
	//DocumentMasterDTO?????? ??????????????? ????????????????????? ???????????????.
	@Override
	public ArrayList<HashMap<String, Object>> getItemsToList(DocumentMasterDTO document) {
		ArrayList<HashMap<String, Object>> items = estimateDao.getItemsToList(document);
		return items;
	}
	
	//?????????list??? map??? ????????? contents??? ??????????????? ??????. (??????ozr??????????????? key?????? item?????????+index??? ?????????????????????.)
	//????????? ?????? ????????? ???????????? ?????? ??????????????? ????????????.
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
		
		//????????? ????????? ???????????? ???????????? ??????????????? ??????.
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
	
	@Override
	public ArrayList<DocumentTypeDTO> getEstimateTypeList() {
		ArrayList<DocumentTypeDTO> typeList = estimateDao.getEstimateTypeList();
		return typeList;
	}
//-------------------------------------------------------------------------------------------------------	
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@Override
	public <T extends EstimateCommonDTO> HashMap<String, String> insertDocument(UserInformDTO user, HttpSession session, Model model, String jsonStr, Class<T> type) {
		T documentDTO = jacksonUtility.readValue(jsonStr, type);
		//??????
		String documentNum = this.getDocoumentNum();
		//??????????????????
		documentDTO.setDocumentNum(documentNum);
		documentDTO.setUpdater(user.getUserNum());
		
		HashMap<String, String> result = new HashMap<String, String>();
		EstimateCommonDTO masterInform = (EstimateCommonDTO)documentDTO;
		int result0 = estimateDao.insertMasterInform(masterInform);
		if(result0 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "????????????????????????");
			return result;
		}
		int result1 = estimateDao.insertDocument(documentDTO);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "?????????????????????????????????");
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
			result.put("error", "????????????????????????????????????");
			return result;
		}
		int result1 = estimateDao.updateDocument(documentDTO);
		if(result1 != 1) {
			result.put("errorFlag", "1");
			result.put("error", "?????????????????????????????????");
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
