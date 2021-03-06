package jp.co.interline.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import jp.co.interline.dto.CompanyDTO;
import jp.co.interline.dto.DepartmentDTO;
import jp.co.interline.dto.FileNamesDTO;
import jp.co.interline.dto.PositionDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.service.CompanyService;
import jp.co.interline.service.FileService;
import jp.co.interline.service.GetProperties;

/**
 * Handles requests for the application home page.
 */
@Controller
public class CompanyController {
	@Autowired
	CompanyService companyService;
	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
	
	@RequestMapping(value = "/all/companyList", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String companyList(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("CompanyController.companyList,user:{}",user.getUserNum());
		ArrayList<CompanyDTO> companyList = companyService.getCompanyList();
		model.addAttribute("companyList", companyList);
		return "companyInform/companyList";
	}
	
	@RequestMapping(value = "/all/companyReg", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String companyReg(HttpSession session, Model model) {
		return "companyInform/companyReg";
	}
	
	@RequestMapping(value = "/all/insertCompany", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String insertCompany(HttpSession session, Model model, CompanyDTO company) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		company.setUpdater(user.getUserNum());
		logger.debug("{}???insertCompany??????", user.getUserNum());
		logger.debug("company : {}", company);
		int result = companyService.insertCompany(company);
		if (result != 1) {
			logger.info("failed for inserting companyInform");
		}else {
			logger.info("companyInform inserted");
		}
		return "redirect:/all/companyList";
	}
	
	@RequestMapping(value = "/all/companyMod", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String companyMod(HttpSession session, Model model, int companyInformNum) {
		CompanyDTO company = companyService.getCompany(companyInformNum);
		model.addAttribute("company", company);
		return "companyInform/companyMod";
	}
	
	@RequestMapping(value = "/all/updateCompany", method = RequestMethod.POST)
	public String updateCompany(HttpSession session, Model model, CompanyDTO company) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		company.setUpdater(user.getUserNum());
		logger.debug("{}???updateCompany??????", user.getUserNum());
		logger.debug("companyInform : {}", company);
		int result = companyService.updateCompany(company);
		if (result != 1) {
			System.out.println("failed for updating companyInform");
		}else {
			logger.info("companyInform updated");
		}
		return "redirect:/all/companyList";
	}
	
	@RequestMapping(value = "/all/deleteCompany", method = RequestMethod.POST)
	public String deleteCompany(HttpSession session, Model model, int companyInformNum) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("{}???deleteCompany??????", user.getUserNum());
		logger.debug("companyInformNum : {}", companyInformNum);
		int result = companyService.deleteCompany(companyInformNum);
		if (result != 1) {
			logger.info("failed for deleting companyInform");
		}
		logger.info("companyInform deleted");
		return "redirect:/all/companyList";
	}
	
	@RequestMapping(value = "/all/imgList", method = RequestMethod.GET)
	public String imgList(HttpSession session, Model model) {
		HashMap<String,String> fileNamesMap = companyService.getfileNames();
		model.addAttribute("stampFileName", fileNamesMap.get("stamp"));
		model.addAttribute("logoFileName", fileNamesMap.get("logo"));
		return "companyInform/imgList";
	}
	
	@Transactional(rollbackFor = {Exception.class, DataAccessException.class})
	@RequestMapping(value = "/all/uploadImgFile", method = RequestMethod.POST)
	public String uploadImgFile(HttpSession session, MultipartFile imgFile, String category) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		GetProperties properties= new GetProperties();
		String projectRoot = properties.getProjectRoot();
		String path =  session.getServletContext().getRealPath("");
		path = path.replaceFirst(projectRoot, "files");
		path = path+ "estimateSystem/uploaded";
		path = path.replace('/', File.separatorChar);
		logger.info("save path : {}",path);
		
		if (!imgFile.isEmpty()) {		//?????? ?????? ??????  ????????? ???????????????????????? ????????? ?????????????????? ???????????? ?????????????????? ?????????????????????????????? ????????? ????????? ??????????????? ?????????.
			String savedfile = FileService.saveFile(imgFile, path, user.getUserId(),category);
			logger.info("fileName : {}",savedfile);
			//????????? ?????? ?????? ??????
			int result = companyService.setFileName(category, savedfile);
		}
		return "redirect:/all/imgList";
	}
	
	//??????,???????????? ???????????? ????????? ??????
	@RequestMapping(value = "/all/groupList", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String groupList(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("CompanyController.groupList,user:{}",user.getUserNum());
		return "companyInform/groupList";
	}
	
	//????????? ??????, ??????, ??????, ??????. groupList???????????? position??? ???????????? html???????????? ?????????.
	@RequestMapping(value = "/all/positionListHtml", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String positionListHtml(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("CompanyController.positionListHTML,user:{}",user.getUserNum());
		
		ArrayList<PositionDTO> positionList = companyService.getPositionList();
		model.addAttribute("positionList", positionList);
		return "companyInform/positionListHtml";
	}
	@RequestMapping(value = "/all/insertPosition", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String insertPosition(HttpSession session, Model model, PositionDTO position) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("CompanyController.insertPosition,user:{}",user.getUserNum());
		int result = companyService.insertPosition(position);
		ArrayList<PositionDTO> positionList = companyService.getPositionList();
		model.addAttribute("positionList", positionList);
		return "companyInform/positionListHtml";
	}
	@RequestMapping(value = "/all/deletePosition", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String deletePosition(HttpSession session, Model model, PositionDTO position) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("CompanyController.deletePosition,user:{}",user.getUserNum());
		int result = companyService.deletePosition(position);
		ArrayList<PositionDTO> positionList = companyService.getPositionList();
		model.addAttribute("positionList", positionList);
		return "companyInform/positionListHtml";
	}
	@RequestMapping(value = "/all/updatePosition", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String updatePosition(HttpSession session, Model model, PositionDTO position) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("CompanyController.updatePosition,user:{}",user.getUserNum());
		int result = companyService.updatePosition(position);
		ArrayList<PositionDTO> positionList = companyService.getPositionList();
		model.addAttribute("positionList", positionList);
		return "companyInform/positionListHtml";
	}
	
	
	//????????? ??????, ??????, ??????, ??????. groupList???????????? department??? ???????????? html???????????? ?????????.
	@RequestMapping(value = "/all/departmentListHtml", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String departmentListHtml(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("CompanyController.departmentListHTML,user:{}",user.getUserNum());
		
		ArrayList<DepartmentDTO> departmentList = companyService.getDepartmentList();
		model.addAttribute("departmentList", departmentList);
		return "companyInform/departmentListHtml";
	}
	@RequestMapping(value = "/all/insertDepartment", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String insertDepartment(HttpSession session, Model model, DepartmentDTO department) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("CompanyController.insertDepartment,user:{}",user.getUserNum());
		int result = companyService.insertDepartment(department);
		ArrayList<DepartmentDTO> departmentList = companyService.getDepartmentList();
		model.addAttribute("departmentList", departmentList);
		return "companyInform/departmentListHtml";
	}
	@RequestMapping(value = "/all/deleteDepartment", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String deleteDepartment(HttpSession session, Model model, DepartmentDTO department) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("CompanyController.departmentListHTML,user:{}",user.getUserNum());
		int result = companyService.deleteDepartment(department);
		ArrayList<DepartmentDTO> departmentList = companyService.getDepartmentList();
		model.addAttribute("departmentList", departmentList);
		return "companyInform/departmentListHtml";
	}
	@RequestMapping(value = "/all/updateDepartment", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String updateDepartment(HttpSession session, Model model, DepartmentDTO department) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("CompanyController.updateDepartment,user:{}",user.getUserNum());
		int result = companyService.updateDepartment(department);
		ArrayList<DepartmentDTO> departmentList = companyService.getDepartmentList();
		model.addAttribute("departmentList", departmentList);
		return "companyInform/departmentListHtml";
	}
}
