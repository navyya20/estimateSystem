package jp.co.interline.dto.billSystem.billD;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.interline.dto.billSystem.BillCommonDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillDDTO extends BillCommonDTO{
	String billDate;
	
	String address;
	String stamp;
	
	@JsonProperty("items")
	ArrayList<BillDItemDTO> items;
	
	String sum;
	String taxRate;
	String tax;
	String discountRate;
	String discount;
	String sumWithTax;
	String sumWithTax2;	
	
	String note;
	
	String bnSwiftCode;
	String bnBankName;
	String bnBranchName;
	String bnBankAddress;
	String bnName;
	String bnAddress;	
	String bnAccountNumber;	
	String bnAccountName;	
	String imSwiftCode;	
	String imBankName;	
	
	String state;
	int workflowNum;
	String insertDate;
	String updateDate;
	int updater;
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStamp() {
		return stamp;
	}
	public void setStamp(String stamp) {
		this.stamp = stamp;
	}
	public ArrayList<BillDItemDTO> getItems() {
		return items;
	}
	public void setItems(ArrayList<BillDItemDTO> items) {
		this.items = items;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public String getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getBnSwiftCode() {
		return bnSwiftCode;
	}
	public void setBnSwiftCode(String bnSwiftCode) {
		this.bnSwiftCode = bnSwiftCode;
	}
	public String getBnBankName() {
		return bnBankName;
	}
	public void setBnBankName(String bnBankName) {
		this.bnBankName = bnBankName;
	}
	public String getBnBranchName() {
		return bnBranchName;
	}
	public void setBnBranchName(String bnBranchName) {
		this.bnBranchName = bnBranchName;
	}
	public String getBnBankAddress() {
		return bnBankAddress;
	}
	public void setBnBankAddress(String bnBankAddress) {
		this.bnBankAddress = bnBankAddress;
	}
	public String getBnName() {
		return bnName;
	}
	public void setBnName(String bnName) {
		this.bnName = bnName;
	}
	public String getBnAddress() {
		return bnAddress;
	}
	public void setBnAddress(String bnAddress) {
		this.bnAddress = bnAddress;
	}
	public String getBnAccountNumber() {
		return bnAccountNumber;
	}
	public void setBnAccountNumber(String bnAccountNumber) {
		this.bnAccountNumber = bnAccountNumber;
	}
	public String getBnAccountName() {
		return bnAccountName;
	}
	public void setBnAccountName(String bnAccountName) {
		this.bnAccountName = bnAccountName;
	}
	public String getImSwiftCode() {
		return imSwiftCode;
	}
	public void setImSwiftCode(String imSwiftCode) {
		this.imSwiftCode = imSwiftCode;
	}
	public String getImBankName() {
		return imBankName;
	}
	public void setImBankName(String imBankName) {
		this.imBankName = imBankName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	
}
