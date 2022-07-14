package jp.co.interline.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.interline.dao.CompanyDAO;
import jp.co.interline.dto.CompanyDTO;
import jp.co.interline.dto.DepartmentDTO;
import jp.co.interline.dto.FileNamesDTO;
import jp.co.interline.dto.PositionDTO;

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

	@Override
	public HashMap<String,String> getfileNames() {
		ArrayList<FileNamesDTO> fileNames = companyDao.getfileNames();
		HashMap<String,String> fileNamesMap = new HashMap<>();
		for (FileNamesDTO fileNamesDTO : fileNames) {
			fileNamesMap.put(fileNamesDTO.getCategory(), fileNamesDTO.getFileName());
		}
		return fileNamesMap;
	}
	
	@Override
	public ArrayList<PositionDTO> getPositionList() {
		ArrayList<PositionDTO> positionList = companyDao.getPositionList();
		return positionList;
	}
	@Override
	public int insertPosition(PositionDTO position) {
		int result = companyDao.insertPosition(position);
		return result;
	}
	@Override
	public int deletePosition(PositionDTO position) {
		int result = companyDao.deletePosition(position);
		return result;
	}
	@Override
	public int updatePosition(PositionDTO position) {
		int result = companyDao.updatePosition(position);
		return result;
	}
	
	@Override
	public ArrayList<DepartmentDTO> getDepartmentList() {
		ArrayList<DepartmentDTO> departmentList = companyDao.getDepartmentList();
		return departmentList;
	}
	@Override
	public int insertDepartment(DepartmentDTO department) {
		int result = companyDao.insertDepartment(department);
		return result;
	}
	@Override
	public int deleteDepartment(DepartmentDTO department) {
		int result = companyDao.deleteDepartment(department);
		return result;
	}
	@Override
	public int updateDepartment(DepartmentDTO department) {
		int result = companyDao.updateDepartment(department);
		return result;
	}

	
}
