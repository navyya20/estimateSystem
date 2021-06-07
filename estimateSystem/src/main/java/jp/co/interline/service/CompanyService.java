package jp.co.interline.service;

import java.util.ArrayList;

import jp.co.interline.dto.CompanyDTO;
import jp.co.interline.dto.FileNamesDTO;


public interface CompanyService {

	ArrayList<CompanyDTO> getCompanyList();

	int insertCompany(CompanyDTO company);

	CompanyDTO getCompany(int companyInformNum);

	int updateCompany(CompanyDTO company);

	int deleteCompany(int companyInformNum);

	int setFileName(String category, String savedfile);

	FileNamesDTO getfileName(String string);
	
	
}
