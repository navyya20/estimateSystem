package jp.co.interline.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import jp.co.interline.dto.DocumentMasterDTO;
import jp.co.interline.dto.DocumentTypeDTO;
import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.estimateSystem.EstimateCommonDTO;
import jp.co.interline.dto.estimateSystem.estimateLanguage.EstimateLanguageDTO;
import jp.co.interline.dto.estimateSystem.estimateLanguage.EstimateLanguageItemDTO;
import jp.co.interline.dto.estimateSystem.estimateSi.EstimateSiDTO;
import jp.co.interline.dto.estimateSystem.estimateSi.EstimateSiItemDTO;
import jp.co.interline.dto.estimateSystem.estimateSolution.EstimateSolutionDTO;
import jp.co.interline.dto.estimateSystem.estimateSolution.EstimateSolutionItemDTO;

public interface EstimateService {

	String getDocoumentNum();

	ArrayList<EstimateListDTO> getEstimateList(Model model, UserInformDTO user, String option, int page, int countPerPage);

	int deleteSheet(String documentNum);
	
	String returnFileName(ArrayList<SystemDTO> systems, String category);

	SystemDTO getEstimate(SystemDTO system);

	DocumentMasterDTO getDocumentMaster(String documentNum);

	HashMap<String, String> getDocumentToHashMap(DocumentMasterDTO document);

	ArrayList<HashMap<String, Object>> getItemsToList(DocumentMasterDTO document);

	void makeHashMap(HashMap<String, String> contents, ArrayList<HashMap<String, Object>> items);
	
	<T extends EstimateCommonDTO> HashMap<String, String> insertDocument(UserInformDTO user, HttpSession session, Model model, String jsonStr, Class<T> type);

	<T extends EstimateCommonDTO> HashMap<String, String> updateDocument(UserInformDTO user, HttpSession session, Model model, String jsonStr, Class<T> type);
	
	void test(String string);

	void test2(String string);

	ArrayList<DocumentTypeDTO> getEstimateTypeList();

}
