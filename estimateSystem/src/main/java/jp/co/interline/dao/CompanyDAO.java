package jp.co.interline.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.interline.dto.CompanyDTO;
import jp.co.interline.dto.DepartmentDTO;
import jp.co.interline.dto.FileNamesDTO;
import jp.co.interline.dto.PositionDTO;

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
	public ArrayList<PositionDTO> getPositionList() {
		CompanyMapper mapper = sqlsession.getMapper(CompanyMapper.class);
		ArrayList<PositionDTO> positionList = mapper.getPositionList();
		return positionList;
	}
	public int insertPosition(PositionDTO position) {
		CompanyMapper mapper = sqlsession.getMapper(CompanyMapper.class);
		int result = mapper.insertPosition(position);
		return result;
	}
	public int deletePosition(PositionDTO position) {
		CompanyMapper mapper = sqlsession.getMapper(CompanyMapper.class);
		int result = mapper.deletePosition(position);// TODO Auto-generated method stub
		return result;
	}
	public int updatePosition(PositionDTO position) {
		CompanyMapper mapper = sqlsession.getMapper(CompanyMapper.class);
		int result = mapper.updatePosition(position);// TODO Auto-generated method stub
		return result;
	}
	
	public ArrayList<DepartmentDTO> getDepartmentList() {
		CompanyMapper mapper = sqlsession.getMapper(CompanyMapper.class);
		ArrayList<DepartmentDTO> departmentList = mapper.getDepartmentList();
		return departmentList;
	}
	public int insertDepartment(DepartmentDTO department) {
		CompanyMapper mapper = sqlsession.getMapper(CompanyMapper.class);
		int result = mapper.insertDepartment(department);
		return result;
	}
	public int deleteDepartment(DepartmentDTO department) {
		CompanyMapper mapper = sqlsession.getMapper(CompanyMapper.class);
		int result = mapper.deleteDepartment(department);
		return result;
	}
	public int updateDepartment(DepartmentDTO department) {
		CompanyMapper mapper = sqlsession.getMapper(CompanyMapper.class);
		int result = mapper.updateDepartment(department);
		return result;
	}
	

}
