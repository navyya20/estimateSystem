package jp.co.interline.dto;

public class BillDItemDTO {
	int rowNum;
	String itemName;
	String unitPrice;
	String amount;
	String price;
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
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
		return "BillDItemDTO [rowNum=" + rowNum + ", itemName=" + itemName + ", amount=" + amount + ", unitPrice="
				+ unitPrice + ", price=" + price + "]";
	}
	
	
}
