package jp.co.interline.dto.billSystem.monthlyBillTotal;

public class MonthlyBillTotalAjaxDTO {
	String documentNum;
	String billDate;
	int userNum;
	String documentName;
	String receiver;
	String sumWithTax;
	String sumWithTax2;
	String insertDate;
	String updateDate;
	int updater;
	String documentTypeName;
	
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
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
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
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	@Override
	public String toString() {
		return "MonthlyBillTotalAjaxDTO [documentNum=" + documentNum + ", userNum=" + userNum + ", documentName="
				+ documentName + ", receiver=" + receiver + ", sumWithTax=" + sumWithTax + ", insertDate=" + insertDate
				+ ", updateDate=" + updateDate + ", updater=" + updater + ", documentTypeName=" + documentTypeName
				+ "]";
	}
	
	
}
