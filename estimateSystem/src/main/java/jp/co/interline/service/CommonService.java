package jp.co.interline.service;

import java.util.ArrayList;

import jp.co.interline.dto.SystemDTO;

public interface CommonService {

	String returnFileName(ArrayList<SystemDTO> systems, String category);	
	
}
