package jp.co.interline.service;

import java.util.ArrayList;

import jp.co.interline.dto.AuthDTO;
import jp.co.interline.dto.DepartmentDTO;
import jp.co.interline.dto.PositionDTO;

public interface BasicService {
	public ArrayList<DepartmentDTO> getDepartments();
	public ArrayList<PositionDTO> getPositions();
	public ArrayList<AuthDTO> getAuths();
}
