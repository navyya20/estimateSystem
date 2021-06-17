package jp.co.interline.dao;

import java.util.ArrayList;

import jp.co.interline.dto.SystemDTO;





public interface SystemMapper {

	SystemDTO getNumber(String string);

	ArrayList<SystemDTO> getFileNames();




}
