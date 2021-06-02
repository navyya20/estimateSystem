package jp.co.interline.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.interline.dto.AuthDTO;
import jp.co.interline.dto.DepartmentDTO;
import jp.co.interline.dto.PositionDTO;

@Repository
public class BasicDAO implements BasicMapper {
	@Autowired
	SqlSession sqlsession;
	
	@Override
	public ArrayList<DepartmentDTO> getDepartments() {
		BasicMapper mapper = sqlsession.getMapper(BasicMapper.class);
		ArrayList<DepartmentDTO> departments = mapper.getDepartments();
		return departments;
	}
	
	@Override
	public ArrayList<PositionDTO> getPositions() {
		BasicMapper mapper = sqlsession.getMapper(BasicMapper.class);
		ArrayList<PositionDTO> positions = mapper.getPositions();
		return positions;
	}
	
	@Override
	public ArrayList<AuthDTO> getAuths() {
		BasicMapper mapper = sqlsession.getMapper(BasicMapper.class);
		ArrayList<AuthDTO> auths = mapper.getAuths();
		return auths;
	}

	
}
