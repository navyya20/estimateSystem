package jp.co.interline.dao;

import java.util.ArrayList;

import jp.co.interline.dto.CompanyDTO;
import jp.co.interline.dto.FileNamesDTO;



public interface CompanyMapper {

	ArrayList<CompanyDTO> getCompanyList();

	int insertCompany(CompanyDTO company);

	int updateCompany(CompanyDTO company);

	CompanyDTO getCompany(int companyInformNum);

	int deleteCompany(int companyInformNum);

	int setFileName(FileNamesDTO file);

	FileNamesDTO getfileName(String category);


	
}
