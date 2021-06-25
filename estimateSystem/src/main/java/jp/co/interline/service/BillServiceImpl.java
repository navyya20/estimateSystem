package jp.co.interline.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.co.interline.dao.BillDAO;
import jp.co.interline.dao.SystemDAO;
import jp.co.interline.dao.WorkflowDAO;
import jp.co.interline.dto.BillSheet1DTO;
import jp.co.interline.dto.BillSheet1ItemsRecieveDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.EstimateSheet1DTO;
import jp.co.interline.dto.EstimateSheet1ItemsRecieveDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.UserInformWithOptionDTO;

@Service
public class BillServiceImpl implements BillService {
	@Autowired
	WorkflowDAO workflowDao;
	
	@Autowired
	BillDAO billDao;
	
	@Autowired
	SystemDAO systemDao;
	
	private static final int countPerPage=20;	
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
	public ArrayList<EstimateListDTO> getBillList(Model model, UserInformDTO user, String option, int page) {
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

	@Override
	public int insertBillSheet1(BillSheet1DTO billSheet1) {
		//청구서 단독 작성시 estimateNum이 ""로 들어옴. 그대로 넣으면 유니크 속성에 걸림.
		if (billSheet1.getEstimateNum().equals("")) {
			billSheet1.setEstimateNum(null);
		}
		
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
		billSheet1.setStampFileName(stampFileName);
		billSheet1.setLogoFileName(logoFileName);
		int result = billDao.insertBillSheet1(billSheet1);
		return result;
	}

	@Override
	public int insertBillSheet1Items(BillSheet1ItemsRecieveDTO billSheet1ItemsReciever) {
		
		int result = billDao.insertBillSheet1Items(billSheet1ItemsReciever);
		return result;
	}

	@Override
	public SystemDTO getBillType(int documentTypeNum) {
		SystemDTO system = billDao.getBillType(documentTypeNum);
		return system;
	}

	@Override
	public SystemDTO getBillTypeName(int documentTypeNum) {
		SystemDTO system = billDao.getBillTypeName(documentTypeNum);
		return system;
	}

	@Override
	public int updateBillSheet1(BillSheet1DTO billSheet1) {
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
		billSheet1.setStampFileName(stampFileName);
		billSheet1.setLogoFileName(logoFileName);
		int result = billDao.updateBillSheet1(billSheet1);
		return result;
	}

	@Override
	public int updateBillSheet1Items(BillSheet1ItemsRecieveDTO billSheet1ItemsReciever) {
		int result = billDao.updateBillSheet1Items(billSheet1ItemsReciever);
		return result;
	}

	@Override
	public BillSheet1DTO getBillSheet1ByDocumentNum(String documentNum) {
		BillSheet1DTO billSheet1DTO = billDao.getBillSheet1ByDocumentNum(documentNum);
		return billSheet1DTO;
	}




	
	
	
}
