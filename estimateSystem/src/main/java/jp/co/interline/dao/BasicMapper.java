package jp.co.interline.dao;

import java.util.ArrayList;

import jp.co.interline.dto.AuthDTO;
import jp.co.interline.dto.DepartmentDTO;
import jp.co.interline.dto.PositionDTO;



public interface BasicMapper {

	ArrayList<DepartmentDTO> getDepartments();

	ArrayList<PositionDTO> getPositions();

	ArrayList<AuthDTO> getAuths();
	
}
