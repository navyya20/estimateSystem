package jp.co.interline.dto.billSystem.billSi;

public class BillSiItemDTO {
	
	int rowNum;
	String monthlyUnitPrice;
	String standardMin;
	String standardMax;
	String workTime;
	String extraTime;
	String overTimeUnitPrice;
	String underTimeUnitPrice;
	String price;
	String expense;
	String benefit;
	String subtotal;
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public String getMonthlyUnitPrice() {
		return monthlyUnitPrice;
	}
	public void setMonthlyUnitPrice(String monthlyUnitPrice) {
		this.monthlyUnitPrice = monthlyUnitPrice;
	}
	public String getStandardMin() {
		return standardMin;
	}
	public void setStandardMin(String standardMin) {
		this.standardMin = standardMin;
	}
	public String getStandardMax() {
		return standardMax;
	}
	public void setStandardMax(String standardMax) {
		this.standardMax = standardMax;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getExtraTime() {
		return extraTime;
	}
	public void setExtraTime(String extraTime) {
		this.extraTime = extraTime;
	}
	public String getOverTimeUnitPrice() {
		return overTimeUnitPrice;
	}
	public void setOverTimeUnitPrice(String overTimeUnitPrice) {
		this.overTimeUnitPrice = overTimeUnitPrice;
	}
	public String getUnderTimeUnitPrice() {
		return underTimeUnitPrice;
	}
	public void setUnderTimeUnitPrice(String underTimeUnitPrice) {
		this.underTimeUnitPrice = underTimeUnitPrice;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getExpense() {
		return expense;
	}
	public void setExpense(String expense) {
		this.expense = expense;
	}
	public String getBenefit() {
		return benefit;
	}
	public void setBenefit(String benefit) {
		this.benefit = benefit;
	}
	public String getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
	@Override
	public String toString() {
		return "BillSiItemsRecieveDTO [rowNum=" + rowNum + ", monthlyUnitPrice=" + monthlyUnitPrice + ", standardMin="
				+ standardMin + ", standardMax=" + standardMax + ", workTime=" + workTime + ", extraTime=" + extraTime
				+ ", overTimeUnitPrice=" + overTimeUnitPrice + ", underTimeUnitPrice=" + underTimeUnitPrice + ", price="
				+ price + ", expense=" + expense + ", benefit=" + benefit + ", subtotal=" + subtotal + "]";
	}
	
}
