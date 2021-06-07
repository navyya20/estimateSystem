package jp.co.interline.dto;

public class WorkflowInformDTO {
	int workFlowInformNum;
	int systemNum;
	int approver1;
	int approver2;
	int approver3;
	String approver1Name;
	String approver2Name;
	String approver3Name;
	String targetKey;
	String insertDate;
	String updateDate;
	String updater;
	int order;
	public int getWorkFlowInformNum() {
		return workFlowInformNum;
	}
	public void setWorkFlowInformNum(int workFlowInformNum) {
		this.workFlowInformNum = workFlowInformNum;
	}
	public int getSystemNum() {
		return systemNum;
	}
	public void setSystemNum(int systemNum) {
		this.systemNum = systemNum;
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
	public String getTargetKey() {
		return targetKey;
	}
	public void setTargetKey(String targetKey) {
		this.targetKey = targetKey;
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
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	public String getApprover1Name() {
		return approver1Name;
	}
	public void setApprover1Name(String approver1Name) {
		this.approver1Name = approver1Name;
	}
	public String getApprover2Name() {
		return approver2Name;
	}
	public void setApprover2Name(String approver2Name) {
		this.approver2Name = approver2Name;
	}
	public String getApprover3Name() {
		return approver3Name;
	}
	public void setApprover3Name(String approver3Name) {
		this.approver3Name = approver3Name;
	}
	@Override
	public String toString() {
		return "WorkflowDTO [workFlowInformNum=" + workFlowInformNum + ", systemNum=" + systemNum + ", approver1="
				+ approver1 + ", approver2=" + approver2 + ", approver3=" + approver3 + ", targetKey=" + targetKey
				+ ", insertDate=" + insertDate + ", updateDate=" + updateDate + ", updater=" + updater + "]";
	}
}
