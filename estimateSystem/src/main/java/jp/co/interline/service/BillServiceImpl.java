package jp.co.interline.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.interline.dao.BillDAO;
import jp.co.interline.dao.SystemDAO;
import jp.co.interline.dao.WorkflowDAO;
import jp.co.interline.dto.BillSheet1DTO;
import jp.co.interline.dto.BillSheet1ItemsRecieveDTO;
import jp.co.interline.dto.EstimateSheet1DTO;
import jp.co.interline.dto.EstimateSheet1ItemsRecieveDTO;
import jp.co.interline.dto.SystemDTO;

@Service
public class BillServiceImpl implements BillService {
	@Autowired
	WorkflowDAO workflowDao;
	
	@Autowired
	BillDAO billDao;
	
	@Autowired
	SystemDAO systemDao;
	
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

	@Override
	public int insertBillSheet1(BillSheet1DTO billSheet1) {
		
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
