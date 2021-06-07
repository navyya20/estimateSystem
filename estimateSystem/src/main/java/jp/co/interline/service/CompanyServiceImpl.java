package jp.co.interline.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.interline.dao.CompanyDAO;
import jp.co.interline.dto.CompanyDTO;
import jp.co.interline.dto.FileNamesDTO;

@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	CompanyDAO companyDao;

	@Override
	public ArrayList<CompanyDTO> getCompanyList() {
		ArrayList<CompanyDTO> companyList = companyDao.getCompanyList();
		return companyList;
	}
	
	@Override
	public int insertCompany(CompanyDTO company) {
		int result = companyDao.insertCompany(company);
		return result;
	}

	@Override
	public int updateCompany(CompanyDTO company) {
		int result = companyDao.updateCompany(company);
		return result;
	}

	@Override
	public CompanyDTO getCompany(int companyInformNum) {
		CompanyDTO company = companyDao.getCompany(companyInformNum);
		return company;
	}

	@Override
	public int deleteCompany(int companyInformNum) {
		int result = companyDao.deleteCompany(companyInformNum);
		return result;
	}

	@Override
	public int setFileName(String category, String savedfile) {
		FileNamesDTO file = new FileNamesDTO();
		file.setCategory(category);
		file.setFileName(savedfile);
		int result = companyDao.setFileName(file);
		return result;
	}

	@Override
	public FileNamesDTO getfileName(String category) {
		FileNamesDTO fileName = companyDao.getfileName(category);
		return fileName;
	}
	
}
