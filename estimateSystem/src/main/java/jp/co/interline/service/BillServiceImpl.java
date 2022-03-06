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
import jp.co.interline.dto.BillCDTO;
import jp.co.interline.dto.BillCItemsRecieveDTO;
import jp.co.interline.dto.BillDDTO;
import jp.co.interline.dto.BillDItemDTO;
import jp.co.interline.dto.BillSheet1DTO;
import jp.co.interline.dto.BillSheet1ItemsRecieveDTO;
import jp.co.interline.dto.BillSiDTO;
import jp.co.interline.dto.BillSiItemsRecieveDTO;
import jp.co.interline.dto.BillSolutionDTO;
import jp.co.interline.dto.BillSolutionItemsRecieveDTO;
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
	
	/*
	 * @Override public SystemDTO getBillType(int documentTypeNum) { SystemDTO
	 * system = billDao.getBillType(documentTypeNum); return system; }
	 * 
	 * @Override public SystemDTO getBillTypeName(int documentTypeNum) { SystemDTO
	 * system = billDao.getBillTypeName(documentTypeNum); return system; }
	 */
	
	
////////////////////////////billSolution///////////////////////////////////////////////
	@Override
	public int insertBillSolution(BillSolutionDTO billSolution) {
		ArrayList<SystemDTO> system = systemDao.getFileNames();
		String stampFileName=returnFileName(system, "stamp");
		String logoFileName=returnFileName(system, "logo");
		billSolution.setStampFileName(stampFileName);
		billSolution.setLogoFileName(logoFileName);
		int result = billDao.insertBillSolution(billSolution);
		return result;
	}

	@Override
	public int insertBillSolutionItems(BillSolutionItemsRecieveDTO billSolutionItemsReciever) {
		int result = billDao.insertBillSolutionItems(billSolutionItemsReciever);
		return result;
	}

	@Override
	public int updateBillSolution(BillSolutionDTO billSolution) {
		ArrayList<SystemDTO> system = systemDao.getFileNames();
		String stampFileName=returnFileName(system, "stamp");
		String logoFileName=returnFileName(system, "logo");
		billSolution.setStampFileName(stampFileName);
		billSolution.setLogoFileName(logoFileName);
		int result = billDao.updateBillSolution(billSolution);
		return result;
	}

	@Override
	public int updateBillSolutionItems(BillSolutionItemsRecieveDTO billSolutionItemsReciever) {
		int result = billDao.updateBillSolutionItems(billSolutionItemsReciever);
		return result;
	}

	
	
////////////////////////////billSi///////////////////////////////////////////////
	@Override
	public int insertBillSi(BillSiDTO billSi) {
		ArrayList<SystemDTO> system = systemDao.getFileNames();
		String stampFileName=returnFileName(system, "stamp");
		String logoFileName=returnFileName(system, "logo");
		billSi.setStampFileName(stampFileName);
		billSi.setLogoFileName(logoFileName);
		int result = billDao.insertBillSi(billSi);
		return result;
	}
	
	@Override
	public int insertBillSiItems(BillSiItemsRecieveDTO billSiItemsReciever) {
		int result = billDao.insertBillSiItems(billSiItemsReciever);
		return result;
	}
	
	@Override
	public int updateBillSi(BillSiDTO billSi) {
		ArrayList<SystemDTO> system = systemDao.getFileNames();
		String stampFileName=returnFileName(system, "stamp");
		String logoFileName=returnFileName(system, "logo");
		billSi.setStampFileName(stampFileName);
		billSi.setLogoFileName(logoFileName);
		int result = billDao.updateBillSi(billSi);
		return result;
	}
	
	@Override
	public int updateBillSiItems(BillSiItemsRecieveDTO billSiItemsReciever) {
		int result = billDao.updateBillSiItems(billSiItemsReciever);
		return result;
	}

////////////////////////////billC///////////////////////////////////////////////
	@Override
	public int insertBillC(BillCDTO billC) {
		ArrayList<SystemDTO> system = systemDao.getFileNames();
		String stampFileName=returnFileName(system, "stamp");
		String logoFileName=returnFileName(system, "logo");
		billC.setStampFileName(stampFileName);
		billC.setLogoFileName(logoFileName);
		int result = billDao.insertBillC(billC);
		return result;
	}
	
	@Override
	public int insertBillCItems(BillCItemsRecieveDTO billCItemsReciever) {
		int result = billDao.insertBillCItems(billCItemsReciever);
		return result;
	}
	
	@Override
	public int updateBillC(BillCDTO billC) {
		ArrayList<SystemDTO> system = systemDao.getFileNames();
		String stampFileName=returnFileName(system, "stamp");
		String logoFileName=returnFileName(system, "logo");
		billC.setStampFileName(stampFileName);
		billC.setLogoFileName(logoFileName);
		int result = billDao.updateBillC(billC);
		return result;
	}
	
	@Override
	public int updateBillCItems(BillCItemsRecieveDTO billCItemsReciever) {
		int result = billDao.updateBillCItems(billCItemsReciever);
		return result;
	}
	
	
	
	
	
	
////////////////////////////billD///////////////////////////////////////////////	
	@Override
	public int insertBillD(BillDDTO billD) {
		ArrayList<SystemDTO> system = systemDao.getFileNames();
		String stampFileName=returnFileName(system, "stamp");
		String logoFileName=returnFileName(system, "logo");
		billD.setStampFileName(stampFileName);
		billD.setLogoFileName(logoFileName);
		int result = billDao.insertBillD(billD);
		return result;
	}
	
	@Override
	public int updateBillD(BillDDTO billD) {
		ArrayList<SystemDTO> system = systemDao.getFileNames();
		String stampFileName=returnFileName(system, "stamp");
		String logoFileName=returnFileName(system, "logo");
		billD.setStampFileName(stampFileName);
		billD.setLogoFileName(logoFileName);
		int result = billDao.updateBillD(billD);
		return result;
	}
	
}
