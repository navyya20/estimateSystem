package jp.co.interline.dto.billSystem.billC;

public class BillCItemDTO {
	int rowNum;
	String itemName;
	String amount;
	String unitPrice;
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
		return "BillCItemDTO [rowNum=" + rowNum + ", itemName=" + itemName + ", amount=" + amount + ", unitPrice="
				+ unitPrice + ", price=" + price + "]";
	}
	
}
