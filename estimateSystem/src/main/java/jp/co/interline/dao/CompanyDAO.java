package jp.co.interline.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.interline.dto.CompanyDTO;
import jp.co.interline.dto.FileNamesDTO;

@Repository
public class CompanyDAO implements CompanyMapper {
	@Autowired
	SqlSession sqlsession;

	public ArrayList<CompanyDTO> getCompanyList() {
		CompanyMapper mapper = sqlsession.getMapper(CompanyMapper.class);
		ArrayList<CompanyDTO> companyList = mapper.getCompanyList();
		return companyList;
	}
	public int insertCompany(CompanyDTO company) {
		CompanyMapper mapper = sqlsession.getMapper(CompanyMapper.class);
		int result = mapper.insertCompany(company);
		return result;
	}

	public int updateCompany(CompanyDTO company) {
		CompanyMapper mapper = sqlsession.getMapper(CompanyMapper.class);
		int result = mapper.updateCompany(company);
		return result;
	}

	public CompanyDTO getCompany(int companyInformNum) {
		CompanyMapper mapper = sqlsession.getMapper(CompanyMapper.class);
		CompanyDTO company = mapper.getCompany(companyInformNum);
		return company;
	}

	public int deleteCompany(int companyInformNum) {
		CompanyMapper mapper = sqlsession.getMapper(CompanyMapper.class);
		int result = mapper.deleteCompany(companyInformNum);
		return result;
	}
	public int setFileName(FileNamesDTO file) {
		CompanyMapper mapper = sqlsession.getMapper(CompanyMapper.class);
		int result = mapper.setFileName(file);
		return result;
	}
	public FileNamesDTO getfileName(String category) {
		CompanyMapper mapper = sqlsession.getMapper(CompanyMapper.class);
		FileNamesDTO fileName = mapper.getfileName(category);
		return fileName;
	}
	

}
