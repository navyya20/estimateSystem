package jp.co.interline.service;

import java.util.ArrayList;
import java.util.HashMap;

import jp.co.interline.dto.CompanyDTO;
import jp.co.interline.dto.DepartmentDTO;
import jp.co.interline.dto.FileNamesDTO;
import jp.co.interline.dto.PositionDTO;


public interface CompanyService {

	ArrayList<CompanyDTO> getCompanyList();

	int insertCompany(CompanyDTO company);

	CompanyDTO getCompany(int companyInformNum);

	int updateCompany(CompanyDTO company);

	int deleteCompany(int companyInformNum);

	int setFileName(String category, String savedfile);

	FileNamesDTO getfileName(String string);
	
	HashMap<String,String> getfileNames();

	ArrayList<PositionDTO> getPositionList();

	ArrayList<DepartmentDTO> getDepartmentList();

	int insertPosition(PositionDTO position);
	
	int insertDepartment(DepartmentDTO department);

	int deletePosition(PositionDTO position);

	int updatePosition(PositionDTO position);

	int deleteDepartment(DepartmentDTO department);

	int updateDepartment(DepartmentDTO department);

	
	
	
}
