package jp.co.interline.dto;

public class WorkflowDTO {
	
	int workflowNum;
	int systemNum;
	int userNum;
	String documentNum;
	int approver1;
	int approver2;
	int approver3;
	String approver1State;
	String approver2State;
	String approver3State;
	int presentApproverNum;
	int presentApprover;
	String targetKey;
	String targetValue;
	String insertDate;
	String updateDate;
	int updater;
	
	String documentTypeName;
	String state;
	String userName;
	String userDepartment;
	
	public int getWorkflowNum() {
		return workflowNum;
	}
	public void setWorkflowNum(int workflowNum) {
		this.workflowNum = workflowNum;
	}
	public int getSystemNum() {
		return systemNum;
	}
	public void setSystemNum(int systemNum) {
		this.systemNum = systemNum;
	}
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public String getDocumentNum() {
		return documentNum;
	}
	public void setDocumentNum(String documentNum) {
		this.documentNum = documentNum;
	}
	public int getApprover1() {
		return approver1;
	}
	public void setApprover1(int approver1) {
		this.approver1 = approver1;
	}
	public int getApprover2() {
		return approver2;
	}
	public void setApprover2(int approver2) {
		this.approver2 = approver2;
	}
	public int getApprover3() {
		return approver3;
	}
	public void setApprover3(int approver3) {
		this.approver3 = approver3;
	}
	public String getApprover1State() {
		return approver1State;
	}
	public void setApprover1State(String approver1State) {
		this.approver1State = approver1State;
	}
	public String getApprover2State() {
		return approver2State;
	}
	public void setApprover2State(String approver2State) {
		this.approver2State = approver2State;
	}
	public String getApprover3State() {
		return approver3State;
	}
	public void setApprover3State(String approver3State) {
		this.approver3State = approver3State;
	}
	public int getPresentApproverNum() {
		return presentApproverNum;
	}
	public void setPresentApproverNum(int presentApproverNum) {
		this.presentApproverNum = presentApproverNum;
	}
	public int getPresentApprover() {
		return presentApprover;
	}
	public void setPresentApprover(int presentApprover) {
		this.presentApprover = presentApprover;
	}
	public String getTargetKey() {
		return targetKey;
	}
	public void setTargetKey(String targetKey) {
		this.targetKey = targetKey;
	}
	public String getTargetValue() {
		return targetValue;
	}
	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
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
	
	public String getDocumentTypeName() {
		return documentTypeName;
	}
	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	
}
