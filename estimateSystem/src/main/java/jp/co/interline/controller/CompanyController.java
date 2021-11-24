package jp.co.interline.controller;

import java.io.File;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jp.co.interline.dto.CompanyDTO;
import jp.co.interline.dto.DepartmentDTO;
import jp.co.interline.dto.FileNamesDTO;
import jp.co.interline.dto.PositionDTO;
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
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		return "companyInform/companyReg";
	}
	
	@RequestMapping(value = "/all/insertCompany", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String insertCompany(HttpSession session, Model model, CompanyDTO company) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		company.setUpdater(user.getUserNum());
		logger.debug("{}がinsertCompany実行", user.getUserNum());
		logger.debug("company : {}", company);
		int result = companyService.insertCompany(company);
		if (result != 1) {
			logger.info("failed for deleting userInform");
		}
		logger.info("userInform deleted");
		
		return "redirect:/all/companyList";
	}
	
	@RequestMapping(value = "/all/companyMod", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String companyMod(HttpSession session, Model model, int companyInformNum) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		CompanyDTO company = companyService.getCompany(companyInformNum);
		model.addAttribute("company", company);
		return "companyInform/companyMod";
	}
	
	@RequestMapping(value = "/all/updateCompany", method = RequestMethod.POST)
	public String updateCompany(HttpSession session, Model model, CompanyDTO company) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		company.setUpdater(user.getUserNum());
		logger.debug("{}がupdateCompany実行", user.getUserNum());
		logger.debug("companyInform : {}", company);
		int result = companyService.updateCompany(company);
		if (result != 1) {
			System.out.println("failed for inserting userInform");
		}
		logger.info("userInform updated");
		return "redirect:/all/companyList";
	}
	
	@RequestMapping(value = "/all/deleteCompany", method = RequestMethod.POST)
	public String deleteCompany(HttpSession session, Model model, int companyInformNum) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("{}がdeleteCompany実行", user.getUserNum());
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
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		FileNamesDTO stampFileName = companyService.getfileName("stamp");
		FileNamesDTO logoFileName = companyService.getfileName("logo");
		model.addAttribute("stampFileName", stampFileName);
		model.addAttribute("logoFileName", logoFileName);
		return "companyInform/imgList";
	}
	
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
		
		FileService fileService = new FileService();
		if (!imgFile.isEmpty()) {		//파일 관련 일들  이름을 다시지어준다던가 결로를 정해준다던가 복사해서 저장한다던가 자부자리한것들이많아 하나의 객체로 조종하는게 편하다.
			String savedfile = FileService.saveFile(imgFile, path, user.getUserId(),category);
			logger.info("fileName : {}",savedfile);
			//디비에 파일 이름 저장
			int result = companyService.setFileName(category, savedfile);
			
		}
		return "redirect:/all/imgList";
	}
	
	//역직,부서등을 설정하는 페이지 로드
	@RequestMapping(value = "/all/groupList", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String groupList(HttpSession session, Model model) {
		UserInformDTO user = (UserInformDTO)session.getAttribute("userInform");
		logger.debug("CompanyController.groupList,user:{}",user.getUserNum());
		return "companyInform/groupList";
	}
	
	//역직을 로드, 삭제, 갱신, 삽입. groupList페이지에 position에 해당하는 html내용물을 로드함.
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
	
	
	//부서를 로드, 삭제, 갱신, 삽입. groupList페이지에 department에 해당하는 html내용물을 로드함.
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
