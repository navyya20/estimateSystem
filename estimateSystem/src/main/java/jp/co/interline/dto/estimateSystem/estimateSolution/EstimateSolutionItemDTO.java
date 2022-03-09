package jp.co.interline.dto.estimateSystem.estimateSolution;

public class EstimateSolutionItemDTO {
	
	int rowNum;
	String item;
	String itemName;
	String amount;
	String unit;
	String unitPrice;
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "EstimateSolutionItemDTO [rowNum=" + rowNum + ", item=" + item + ", itemName=" + itemName + ", amount="
				+ amount + ", unit=" + unit + ", unitPrice=" + unitPrice + ", price=" + price + "]";
	}
	
}
