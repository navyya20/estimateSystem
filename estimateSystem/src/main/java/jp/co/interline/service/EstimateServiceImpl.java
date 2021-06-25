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
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.EstimateSheet1DTO;
import jp.co.interline.dto.EstimateSheet1ItemsRecieveDTO;
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
		SystemDTO sys=null;
		String stampFileName="";
		String logoFileName="";
		for (int i = 0; i < system.size(); i++) {
			sys = system.get(i);
			if (sys.getCategory().equals("stamp")) {
				stampFileName=sys.getFileName();
			}
		}
		for (int i = 0; i < system.size(); i++) {
			sys = system.get(i);
			if (sys.getCategory().equals("logo")) {
				logoFileName=sys.getFileName();
			}
		}
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

	@Override
	public int updateEstimateSheet1(EstimateSheet1DTO estimateSheet1) {
		ArrayList<SystemDTO> system = systemDao.getFileNames();
		SystemDTO sys=null;
		String stampFileName="";
		String logoFileName="";
		for (int i = 0; i < system.size(); i++) {
			sys = system.get(i);
			if (sys.getCategory().equals("stamp")) {
				stampFileName=sys.getFileName();
			}
		}
		for (int i = 0; i < system.size(); i++) {
			sys = system.get(i);
			if (sys.getCategory().equals("logo")) {
				logoFileName=sys.getFileName();
			}
		}
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
	public EstimateSheet1DTO getEstimateSheet1ByDocumentNum(String documentNum) {
		EstimateSheet1DTO estimateSheet1DTO = estimateDao.getEstimateSheet1ByDocumentNum(documentNum);
		return estimateSheet1DTO;
	}

	@Override
	public void test() {
		testDTO test = new testDTO();
		ArrayList<HashMap<String, String>> h = test.getHashMapList();
		estimateDao.test(h);
		
	}


	
	
	
}
