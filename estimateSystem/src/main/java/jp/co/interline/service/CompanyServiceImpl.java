package jp.co.interline.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.interline.dao.CompanyDAO;

@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	CompanyDAO companyDao;
	
	
}
