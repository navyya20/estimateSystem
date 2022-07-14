package jp.co.interline.dto.billSystem.monthlyBillTotal;

public class MonthlyBillTotalAjaxForBillSiDTO {
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
	
	String workerName;
	int monthlyUnitPrice;
	int standardMin;
	int standardMax;
	float workTime;
	float extraTime;
	int overTimeUnitPrice;
	int underTimeUnitPrice;
	String price;
	int expense;
	int benefit;
	int subtotal;
	
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
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public int getMonthlyUnitPrice() {
		return monthlyUnitPrice;
	}
	public void setMonthlyUnitPrice(int monthlyUnitPrice) {
		this.monthlyUnitPrice = monthlyUnitPrice;
	}
	public int getStandardMin() {
		return standardMin;
	}
	public void setStandardMin(int standardMin) {
		this.standardMin = standardMin;
	}
	public int getStandardMax() {
		return standardMax;
	}
	public void setStandardMax(int standardMax) {
		this.standardMax = standardMax;
	}
	public float getWorkTime() {
		return workTime;
	}
	public void setWorkTime(float workTime) {
		this.workTime = workTime;
	}
	public float getExtraTime() {
		return extraTime;
	}
	public void setExtraTime(float extraTime) {
		this.extraTime = extraTime;
	}
	public int getOverTimeUnitPrice() {
		return overTimeUnitPrice;
	}
	public void setOverTimeUnitPrice(int overTimeUnitPrice) {
		this.overTimeUnitPrice = overTimeUnitPrice;
	}
	public int getUnderTimeUnitPrice() {
		return underTimeUnitPrice;
	}
	public void setUnderTimeUnitPrice(int underTimeUnitPrice) {
		this.underTimeUnitPrice = underTimeUnitPrice;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getExpense() {
		return expense;
	}
	public void setExpense(int expense) {
		this.expense = expense;
	}
	public int getBenefit() {
		return benefit;
	}
	public void setBenefit(int benefit) {
		this.benefit = benefit;
	}
	public int getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
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
