package jp.co.interline.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.interline.dto.SystemDTO;


@Repository
public class SystemDAO implements SystemMapper {
	@Autowired
	SqlSession sqlsession;

	public SystemDTO getNumber(String string) {
		SystemMapper mapper = sqlsession.getMapper(SystemMapper.class);
		SystemDTO system = mapper.getNumber(string);
		return system;
	}

	public ArrayList<SystemDTO> getFileNames() {
		SystemMapper mapper = sqlsession.getMapper(SystemMapper.class);
		ArrayList<SystemDTO> system = mapper.getFileNames();
		return system;
	}

	
}
