package jp.co.interline.dto;

public class DocumentMasterDTO {
	String documentNum;
	int systemNum;
	String documentTypeName;
	String nextDocumentTypeName;
	int userNum;
	String userName;
	String userDepartment;
	String userPosition;
	int workflowNum;
	String state;
	String insertDate;
	String updateDate;
	int updater;
	String approvedDate;
	String fileName;
	
	public String getDocumentNum() {
		return documentNum;
	}
	public void setDocumentNum(String documentNum) {
		this.documentNum = documentNum;
	}
	public int getSystemNum() {
		return systemNum;
	}
	public void setSystemNum(int systemNum) {
		this.systemNum = systemNum;
	}
	public String getDocumentTypeName() {
		return documentTypeName;
	}
	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}
	public String getNextDocumentTypeName() {
		return nextDocumentTypeName;
	}
	public void setNextDocumentTypeName(String nextDocumentTypeName) {
		this.nextDocumentTypeName = nextDocumentTypeName;
	}
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserDepartment() {
		return userDepartment;
	}
	public void setUserDepartment(String userDepartment) {
		this.userDepartment = userDepartment;
	}
	public String getUserPosition() {
		return userPosition;
	}
	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}
	public int getWorkflowNum() {
		return workflowNum;
	}
	public void setWorkflowNum(int workflowNum) {
		this.workflowNum = workflowNum;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public int getUpdater() {
		return updater;
	}
	public void setUpdater(int updater) {
		this.updater = updater;
	}
	public String getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public String toString() {
		return "DocumentMasterDTO [documentNum=" + documentNum + ", systemNum=" + systemNum + ", documentTypeName="
				+ documentTypeName + ", nextDocumentTypeName=" + nextDocumentTypeName + ", userNum=" + userNum
				+ ", userName=" + userName + ", userDepartment=" + userDepartment + ", userPosition=" + userPosition
				+ ", workflowNum=" + workflowNum + ", state=" + state + ", insertDate=" + insertDate + ", updateDate="
				+ updateDate + ", updater=" + updater + ", approvedDate=" + approvedDate + ", fileName=" + fileName
				+ "]";
	}
	
}
