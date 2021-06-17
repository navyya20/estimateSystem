package jp.co.interline.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;

import jp.co.interline.dto.EstimateListDTO;
import jp.co.interline.dto.EstimateSheet1DTO;
import jp.co.interline.dto.EstimateSheet1ItemsRecieveDTO;
import jp.co.interline.dto.SystemDTO;
import jp.co.interline.dto.UserInformDTO;
import jp.co.interline.dto.UserInformWithOptionDTO;
import jp.co.interline.dto.WorkflowInformDTO;




public interface EstimateMapper {

	int insertEstimateSheet1(EstimateSheet1DTO estimateSheet1);

	int insertEstimateSheet1Items(EstimateSheet1ItemsRecieveDTO estimateSheet1ItemsReciever);

	int getTotalEstimateSheet(UserInformWithOptionDTO userInformWithOption);

	ArrayList<EstimateListDTO> getEstimateList(RowBounds rbs, UserInformWithOptionDTO userInformWithOption);

	int updateEstimateSheet1(EstimateSheet1DTO estimateSheet1);

	int updateEstimateSheet1Items(EstimateSheet1ItemsRecieveDTO estimateSheet1ItemsReciever);

	int deleteSheet(SystemDTO system);

	EstimateSheet1DTO getEstimateSheet1ByDocumentNum(String documentNum);

}
