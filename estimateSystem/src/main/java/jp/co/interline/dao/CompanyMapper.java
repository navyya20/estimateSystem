package jp.co.interline.dao;

import java.util.ArrayList;

import jp.co.interline.dto.CompanyDTO;
import jp.co.interline.dto.DepartmentDTO;
import jp.co.interline.dto.FileNamesDTO;
import jp.co.interline.dto.PositionDTO;



public interface CompanyMapper {

	ArrayList<CompanyDTO> getCompanyList();

	int insertCompany(CompanyDTO company);

	int updateCompany(CompanyDTO company);

	CompanyDTO getCompany(int companyInformNum);

	int deleteCompany(int companyInformNum);

	int setFileName(FileNamesDTO file);

	FileNamesDTO getfileName(String category);

	ArrayList<PositionDTO> getPositionList();
	
	ArrayList<DepartmentDTO> getDepartmentList();

	int insertPosition(PositionDTO position);

	int insertDepartment(DepartmentDTO department);

	int deletePosition(PositionDTO position);

	int updatePosition(PositionDTO position);

	int deleteDepartment(DepartmentDTO department);

	int updateDepartment(DepartmentDTO department);


	
}
