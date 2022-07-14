package jp.co.interline.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.interline.dao.CompanyDAO;
import jp.co.interline.dto.CompanyDTO;
import jp.co.interline.dto.DepartmentDTO;
import jp.co.interline.dto.FileNamesDTO;
import jp.co.interline.dto.PositionDTO;
import jp.co.interline.dto.SystemDTO;

@Service
public class CommonServiceImpl implements CommonService {
	
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
	
}
