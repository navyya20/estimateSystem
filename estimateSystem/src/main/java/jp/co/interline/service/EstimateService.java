package jp.co.interline.service;

import java.util.ArrayList;

import org.springframework.ui.Model;

import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.EstimateSheet1DTO;
import jp.co.interline.dto.EstimateSheet1ItemsRecieveDTO;
import jp.co.interline.dto.EstimateSolutionDTO;
import jp.co.interline.dto.EstimateSolutionItemsRecieveDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;

public interface EstimateService {

	String getDocoumentNum();

	int insertEstimateSheet1(EstimateSheet1DTO estimateSheet1);

	int insertEstimateSheet1Items(EstimateSheet1ItemsRecieveDTO estimateSheet1ItemsReciever);

	ArrayList<EstimateListDTO> getEstimateList(Model model, UserInformDTO user, String option, int page);

	int updateEstimateSheet1(EstimateSheet1DTO estimateSheet1);

	int updateEstimateSheet1Items(EstimateSheet1ItemsRecieveDTO estimateSheet1ItemsReciever);

	int deleteSheet(String documentNum);

	void test();
	
	String returnFileName(ArrayList<SystemDTO> systems, String category);

	int insertEstimateSolution(EstimateSolutionDTO estimateSolution);

	int insertEstimateSolutionItems(EstimateSolutionItemsRecieveDTO estimateSolutionItemsReciever);

	SystemDTO getEstimate(SystemDTO system);

	int updateEstimateSolution(EstimateSolutionDTO estimateSolution);

	int updateEstimateSolutionItems(EstimateSolutionItemsRecieveDTO estimateSolutionItemsReciever);

}
