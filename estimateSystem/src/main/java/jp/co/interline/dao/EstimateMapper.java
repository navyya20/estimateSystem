package jp.co.interline.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;

import jp.co.interline.dto.DocumentMasterDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformWithOptionDTO;
import jp.co.interline.dto.estimateSystem.estimateLanguage.EstimateLanguageDTO;
import jp.co.interline.dto.estimateSystem.estimateLanguage.EstimateLanguageItemDTO;
import jp.co.interline.dto.estimateSystem.estimateSi.EstimateSiDTO;
import jp.co.interline.dto.estimateSystem.estimateSi.EstimateSiItemDTO;
import jp.co.interline.dto.estimateSystem.estimateSolution.EstimateSolutionDTO;
import jp.co.interline.dto.estimateSystem.estimateSolution.EstimateSolutionItemDTO;




public interface EstimateMapper {

	int getTotalEstimateSheet(UserInformWithOptionDTO userInformWithOption);

	ArrayList<EstimateListDTO> getEstimateList(RowBounds rbs, UserInformWithOptionDTO userInformWithOption);

	int deleteSheet(SystemDTO system);

	String getBillNum(String documentNum);

	int insertEstimateSolution(EstimateSolutionDTO estimateSolution);

	int insertEstimateSolutionItems(EstimateSolutionItemDTO estimateSolutionItemsReciever);

	SystemDTO getEstimate(SystemDTO system);

	int updateEstimateSolution(EstimateSolutionDTO estimateSolution);

	int updateEstimateSolutionItems(EstimateSolutionItemDTO estimateSolutionItemsReciever);

	int insertEstimateLanguage(EstimateLanguageDTO estimateLanguage);

	int insertEstimateLanguageItems(EstimateLanguageItemDTO estimateLanguageItemsReciever);

	int updateEstimateLanguage(EstimateLanguageDTO estimateLanguage);

	int updateEstimateLanguageItems(EstimateLanguageItemDTO estimateLanguageItemsReciever);
	

	int insertEstimateSi(EstimateSiDTO estimateSi);

	int insertEstimateSiItems(EstimateSiItemDTO estimateSiItemsReciever);

	int updateEstimateSi(EstimateSiDTO estimateSi);

	int updateEstimateSiItems(EstimateSiItemDTO estimateSiItemsReciever);

	DocumentMasterDTO getDocumentMaster(String documentNum);

	HashMap<String, String> getDocumentToHashMap(DocumentMasterDTO document);

	ArrayList<HashMap<String, Object>> getItemsToList(DocumentMasterDTO document);
	

	void testTest(String s);

	void testTest2(String s);
	
	
}
