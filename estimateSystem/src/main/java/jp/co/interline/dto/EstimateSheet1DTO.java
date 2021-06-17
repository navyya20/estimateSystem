package jp.co.interline.dto;

public class EstimateSheet1DTO {
	String documentNum;
	int userNum;
	String userName;
	String userDepartment;
	String userPosition;
	String estimateDate;
	int documentTypeNum;
	String documentTypeName;
	String supplier;
	String address;
	String post;
	String phoneNumber;
	String representative;
	String stamp;
	String stampFileName;
	String logoFileName;
	String receiver;
	String documentName;
	String deadline;
	String supplyPoint;
	String expirationDate;
	String payCondition;
	String cautions;
	String sum;
	String tax;
	String sumWithTax;
	String sumWithTax2;	
	String state;
	String comment;
	int workflowNum;
	String insertDate;
	String updateDate;
	int updater;
	public String getDocumentNum() {
		return documentNum;
	}
	public void setDocumentNum(String documentNum) {
		this.documentNum = documentNum;
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
	public String getEstimateDate() {
		return estimateDate;
	}
	public void setEstimateDate(String estimateDate) {
		this.estimateDate = estimateDate;
	}
	public int getDocumentTypeNum() {
		return documentTypeNum;
	}
	public void setDocumentTypeNum(int documentTypeNum) {
		this.documentTypeNum = documentTypeNum;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getRepresentative() {
		return representative;
	}
	public void setRepresentative(String representative) {
		this.representative = representative;
	}
	public String getStamp() {
		return stamp;
	}
	public void setStamp(String stamp) {
		this.stamp = stamp;
	}
	public String getStampFileName() {
		return stampFileName;
	}
	public void setStampFileName(String stampFileName) {
		this.stampFileName = stampFileName;
	}
	public String getLogoFileName() {
		return logoFileName;
	}
	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}
	
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getSupplyPoint() {
		return supplyPoint;
	}
	public void setSupplyPoint(String supplyPoint) {
		this.supplyPoint = supplyPoint;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getPayCondition() {
		return payCondition;
	}
	public void setPayCondition(String payCondition) {
		this.payCondition = payCondition;
	}
	
	public String getCautions() {
		return cautions;
	}
	public void setCautions(String cautions) {
		this.cautions = cautions;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	public int getWorkflowNum() {
		return workflowNum;
	}
	public void setWorkflowNum(int workflowNum) {
		this.workflowNum = workflowNum;
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
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getSumWithTax() {
		return sumWithTax;
	}
	public void setSumWithTax(String sumWithTax) {
		this.sumWithTax = sumWithTax;
	}
	public String getSumWithTax2() {
		return sumWithTax2;
	}
	public void setSumWithTax2(String sumWithTax2) {
		this.sumWithTax2 = sumWithTax2;
	}
	public String getDocumentTypeName() {
		return documentTypeName;
	}
	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}
	@Override
	public String toString() {
		return "EstimateSheet1DTO [documentNum=" + documentNum + ", userNum=" + userNum + ", userName=" + userName
				+ ", userDepartment=" + userDepartment + ", userPosition=" + userPosition + ", estimateDate="
				+ estimateDate + ", documentTypeNum=" + documentTypeNum + ", documentTypeName=" + documentTypeName
				+ ", supplier=" + supplier + ", address=" + address + ", post=" + post + ", phoneNumber=" + phoneNumber
				+ ", representative=" + representative + ", stamp=" + stamp + ", stampFileName=" + stampFileName
				+ ", logoFileName=" + logoFileName + ", receiver=" + receiver + ", documentName=" + documentName
				+ ", deadline=" + deadline + ", supplyPoint=" + supplyPoint + ", expirationDate=" + expirationDate
				+ ", payCondition=" + payCondition + ", cautions=" + cautions + ", sum=" + sum + ", tax=" + tax
				+ ", sumWithTax=" + sumWithTax + ", sumWithTax2=" + sumWithTax2 + ", state=" + state + ", comment="
				+ comment + ", workflowNum=" + workflowNum + ", insertDate=" + insertDate + ", updateDate=" + updateDate
				+ ", updater=" + updater + "]";
	}
	
	
	
}