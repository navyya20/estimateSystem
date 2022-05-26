package jp.co.interline.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.interline.dto.DocumentMasterDTO;
import jp.co.interline.dto.DocumentTypeDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformWithOptionDTO;
import jp.co.interline.dto.estimateSystem.EstimateCommonDTO;
import jp.co.interline.dto.estimateSystem.estimateLanguage.EstimateLanguageDTO;
import jp.co.interline.dto.estimateSystem.estimateLanguage.EstimateLanguageItemDTO;
import jp.co.interline.dto.estimateSystem.estimateSi.EstimateSiDTO;
import jp.co.interline.dto.estimateSystem.estimateSi.EstimateSiItemDTO;
import jp.co.interline.dto.estimateSystem.estimateSolution.EstimateSolutionDTO;
import jp.co.interline.dto.estimateSystem.estimateSolution.EstimateSolutionItemDTO;


@Repository
public class EstimateDAO {
	@Autowired
	SqlSession sqlsession;

	public int getTotalEstimateSheet(UserInformWithOptionDTO userInformWithOption) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int total = mapper.getTotalEstimateSheet(userInformWithOption);
		return total;
	}

	

	public ArrayList<EstimateListDTO> getEstimateList(int st, int ctt, UserInformWithOptionDTO userInformWithOption) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		RowBounds rbs = new RowBounds(st,ctt);
		ArrayList<EstimateListDTO> estimateList = mapper.getEstimateList(rbs, userInformWithOption);
		return estimateList;
	}

	public int deleteSheet(SystemDTO system) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.deleteSheet(system);
		return result;
	}

	public SystemDTO getEstimate(SystemDTO system) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		SystemDTO sys = mapper.getEstimate(system);
		return sys;
	}
	
	public String getBillNum(String documentNum) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		String billNum = mapper.getBillNum(documentNum);
		return billNum;
	}

	public int insertEstimateSolution(EstimateSolutionDTO estimateSolution) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.insertEstimateSolution(estimateSolution);
		return result;
	}

	public int insertEstimateSolutionItems(EstimateSolutionItemDTO estimateSolutionItemsReciever) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.insertEstimateSolutionItems(estimateSolutionItemsReciever);
		return result;
	}

	public int updateEstimateSolution(EstimateSolutionDTO estimateSolution) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.updateEstimateSolution(estimateSolution);
		return result;
	}

	public int updateEstimateSolutionItems(EstimateSolutionItemDTO estimateSolutionItemsReciever) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.updateEstimateSolutionItems(estimateSolutionItemsReciever);
		return result;
	}
	
	
	
	
	public int insertEstimateLanguage(EstimateLanguageDTO estimateLanguage) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.insertEstimateLanguage(estimateLanguage);
		return result;
	}

	public int insertEstimateLanguageItems(EstimateLanguageItemDTO estimateLanguageItemsReciever) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.insertEstimateLanguageItems(estimateLanguageItemsReciever);
		return result;
	}

	public int updateEstimateLanguage(EstimateLanguageDTO estimateLanguage) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.updateEstimateLanguage(estimateLanguage);
		return result;
	}

	public int updateEstimateLanguageItems(EstimateLanguageItemDTO estimateLanguageItemsReciever) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.updateEstimateLanguageItems(estimateLanguageItemsReciever);
		return result;
	}

	

	
	public int insertEstimateSi(EstimateSiDTO estimateSi) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.insertEstimateSi(estimateSi);
		return result;
	}

	public int insertEstimateSiItems(EstimateSiItemDTO estimateSiItemsReciever) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.insertEstimateSiItems(estimateSiItemsReciever);
		return result;
	}

	public int updateEstimateSi(EstimateSiDTO estimateSi) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.updateEstimateSi(estimateSi);
		return result;
	}

	public int updateEstimateSiItems(EstimateSiItemDTO estimateSiItemsReciever) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = mapper.updateEstimateSiItems(estimateSiItemsReciever);
		return result;
	}

	public DocumentMasterDTO getDocumentMaster(String documentNum) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		DocumentMasterDTO document = mapper.getDocumentMaster(documentNum);
		return document;
	}

	public HashMap<String, String> getDocumentToHashMap(DocumentMasterDTO document) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		HashMap<String, String> contents = mapper.getDocumentToHashMap(document);
		return contents;
	}

	public ArrayList<HashMap<String, Object>> getItemsToList(DocumentMasterDTO document) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		ArrayList<HashMap<String, Object>> items = mapper.getItemsToList(document);
		return items;
	}
	
	public ArrayList<DocumentTypeDTO> getEstimateTypeList() {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		ArrayList<DocumentTypeDTO> typeList = mapper.getEstimateTypeList();
		return typeList;
	}
	
	///////////////////////////////////////////////////////
	public int insertMasterInform(EstimateCommonDTO masterInform) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		return mapper.insertMasterInform(masterInform);
	}
	
	public int updateMasterInform(EstimateCommonDTO masterInform) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		return mapper.updateMasterInform(masterInform);
	}
	
	public <T extends EstimateCommonDTO> int insertDocument(T dto) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = 0;
		switch (dto.getDocumentTypeName()) {
		case "estimateSolution":
			result = mapper.insertEstimateSolution((EstimateSolutionDTO)dto);
			break;
		case "estimateSi":
			result = mapper.insertEstimateSi((EstimateSiDTO)dto);
			break;
		case "estimateLanguage":
			result = mapper.insertEstimateLanguage((EstimateLanguageDTO)dto);
			break;
		default:
			return result;
		}
		return result;
	}

	public <T extends EstimateCommonDTO> int updateDocument(T dto) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		int result = 0;
		switch (dto.getDocumentTypeName()) {
		case "estimateSolution":
			result = mapper.updateEstimateSolution((EstimateSolutionDTO)dto);
			break;
		case "estimateSi":
			result = mapper.updateEstimateSi((EstimateSiDTO)dto);
			break;
		case "estimateLanguage":
			result = mapper.updateEstimateLanguage((EstimateLanguageDTO)dto);
			break;
		default:
			return result;
		}
		return result;
	}


	public void test(String s) {
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		mapper.testTest(s);
	}

	public void test2(String s) {
		//"".substring(-2);
		EstimateMapper mapper = sqlsession.getMapper(EstimateMapper.class);
		mapper.testTest2(s);
	}

	



	
	
}
