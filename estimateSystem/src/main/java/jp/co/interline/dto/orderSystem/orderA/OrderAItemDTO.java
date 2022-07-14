package jp.co.interline.dto.orderSystem.orderA;

public class OrderAItemDTO {
	
	int rowNum;
	String item;
	String itemName;
	String workStart;
	String workEnd;
	String manMonth;
	String price;
	String note;
	
	String unitPrice;
	String standardMin;
	String standardMax;
	String underTimeUnitPrice;
	String overTimeUnitPrice;
	String note2;
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getWorkStart() {
		return workStart;
	}
	public void setWorkStart(String workStart) {
		this.workStart = workStart;
	}
	public String getWorkEnd() {
		return workEnd;
	}
	public void setWorkEnd(String workEnd) {
		this.workEnd = workEnd;
	}
	public String getManMonth() {
		return manMonth;
	}
	public void setManMonth(String manMonth) {
		this.manMonth = manMonth;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
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
	public String getUnderTimeUnitPrice() {
		return underTimeUnitPrice;
	}
	public void setUnderTimeUnitPrice(String underTimeUnitPrice) {
		this.underTimeUnitPrice = underTimeUnitPrice;
	}
	public String getOverTimeUnitPrice() {
		return overTimeUnitPrice;
	}
	public void setOverTimeUnitPrice(String overTimeUnitPrice) {
		this.overTimeUnitPrice = overTimeUnitPrice;
	}
	public String getNote2() {
		return note2;
	}
	public void setNote2(String note2) {
		this.note2 = note2;
	}
	
	
}
