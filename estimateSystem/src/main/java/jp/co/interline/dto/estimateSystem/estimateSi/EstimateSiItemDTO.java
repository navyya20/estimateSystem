package jp.co.interline.dto.estimateSystem.estimateSi;

public class EstimateSiItemDTO {
	
	int rowNum;
	String item;
	String itemName;
	String workStart;
	String workEnd;
	String unitPrice;
	String manMonth;
	String price;
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
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
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
	@Override
	public String toString() {
		return "EstimateSiItemDTO [rowNum=" + rowNum + ", item=" + item + ", itemName=" + itemName + ", workStart="
				+ workStart + ", workEnd=" + workEnd + ", unitPrice=" + unitPrice + ", manMonth=" + manMonth
				+ ", price=" + price + "]";
	}
	
}
