package jp.co.interline.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import jp.co.interline.dto.DocumentMasterDTO;
import jp.co.interline.dto.EstimateLanguageDTO;
import jp.co.interline.dto.EstimateLanguageItemsRecieveDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.EstimateSheet1DTO;
import jp.co.interline.dto.EstimateSheet1ItemsRecieveDTO;
import jp.co.interline.dto.EstimateSiDTO;
import jp.co.interline.dto.EstimateSiItemsRecieveDTO;
import jp.co.interline.dto.EstimateSolutionDTO;
import jp.co.interline.dto.EstimateSolutionItemsRecieveDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;

public interface EstimateService {

	String getDocoumentNum();

	ArrayList<EstimateListDTO> getEstimateList(Model model, UserInformDTO user, String option, int page, int countPerPage);

	int deleteSheet(String documentNum);
	
	String returnFileName(ArrayList<SystemDTO> systems, String category);

	int insertEstimateSolution(EstimateSolutionDTO estimateSolution);

	int insertEstimateSolutionItems(EstimateSolutionItemsRecieveDTO estimateSolutionItemsReciever);

	SystemDTO getEstimate(SystemDTO system);

	int updateEstimateSolution(EstimateSolutionDTO estimateSolution);

	int updateEstimateSolutionItems(EstimateSolutionItemsRecieveDTO estimateSolutionItemsReciever);

	int insertEstimateLanguage(EstimateLanguageDTO estimateLanguage);

	int insertEstimateLanguageItems(EstimateLanguageItemsRecieveDTO estimateLanguageItemsReciever);

	int updateEstimateLanguage(EstimateLanguageDTO estimateLanguage);

	int updateEstimateLanguageItems(EstimateLanguageItemsRecieveDTO estimateLanguageItemsReciever);

	int insertEstimateSi(EstimateSiDTO estimateSi);

	int insertEstimateSiItems(EstimateSiItemsRecieveDTO estimateSiItemsReciever);

	int updateEstimateSi(EstimateSiDTO estimateSi);

	int updateEstimateSiItems(EstimateSiItemsRecieveDTO estimateSiItemsReciever);

	DocumentMasterDTO getDocumentMaster(String documentNum);

	HashMap<String, String> getDocumentToHashMap(DocumentMasterDTO document);

	ArrayList<HashMap<String, Object>> getItemsToList(DocumentMasterDTO document);

	void makeHashMap(HashMap<String, String> contents, ArrayList<HashMap<String, Object>> items);
	
	void test(String string);

	void test2(String string);

}
