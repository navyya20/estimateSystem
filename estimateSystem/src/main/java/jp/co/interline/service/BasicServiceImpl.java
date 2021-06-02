package jp.co.interline.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.interline.dao.BasicDAO;
import jp.co.interline.dto.AuthDTO;
import jp.co.interline.dto.DepartmentDTO;
import jp.co.interline.dto.PositionDTO;

@Service
public class BasicServiceImpl implements BasicService {
	@Autowired
	BasicDAO basicDao;

	@Override
	public ArrayList<DepartmentDTO> getDepartments() {
		ArrayList<DepartmentDTO> departments = basicDao.getDepartments();
		return departments;
	}

	@Override
	public ArrayList<PositionDTO> getPositions() {
		ArrayList<PositionDTO> positions = basicDao.getPositions();
		return positions;
	}

	@Override
	public ArrayList<AuthDTO> getAuths() {
		ArrayList<AuthDTO> auths = basicDao.getAuths();
		return auths;
	}
	
	
	
}
