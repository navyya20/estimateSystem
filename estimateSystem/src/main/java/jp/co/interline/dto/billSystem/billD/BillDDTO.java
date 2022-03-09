package jp.co.interline.dto.billSystem.billD;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.interline.dto.billSystem.BillCommonDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillDDTO extends BillCommonDTO{
	int systemNum;
	String documentNum;
	String estimateNum;
	int userNum;
	String userName;
	String userDepartment;
	String userPosition;
	String billDate;
	String documentTypeName;
	String nextDocumentTypeName;
	
	String address;
	String stamp;
	String stampFileName;
	String logoFileName;
	String receiver;
	
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
	public int getSystemNum() {
		return systemNum;
	}
	public void setSystemNum(int systemNum) {
		this.systemNum = systemNum;
	}
	public String getDocumentNum() {
		return documentNum;
	}
	public void setDocumentNum(String documentNum) {
		this.documentNum = documentNum;
	}
	public String getEstimateNum() {
		return estimateNum;
	}
	public void setEstimateNum(String estimateNum) {
		this.estimateNum = estimateNum;
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
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getDocumentTypeName() {
		return documentTypeName;
	}
	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}
	public String getNextDocumentTypeName() {
		return nextDocumentTypeName;
	}
	public void setNextDocumentTypeName(String nextDocumentTypeName) {
		this.nextDocumentTypeName = nextDocumentTypeName;
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
	public void setItems(ArrayList<BillDItemDTO> items) {
		this.items = items;
	}
	public ArrayList<BillDItemDTO> getItems() {
		return items;
	}
	
	@Override
	public String toString() {
		return "BillDDTO [systemNum=" + systemNum + ", documentNum=" + documentNum + ", estimateNum=" + estimateNum
				+ ", userNum=" + userNum + ", userName=" + userName + ", userDepartment=" + userDepartment
				+ ", userPosition=" + userPosition + ", billDate=" + billDate + ", documentTypeName=" + documentTypeName
				+ ", nextDocumentTypeName=" + nextDocumentTypeName + ", address=" + address + ", stamp=" + stamp
				+ ", stampFileName=" + stampFileName + ", logoFileName=" + logoFileName + ", receiver=" + receiver
				+ ", sum=" + sum + ", taxRate=" + taxRate + ", tax=" + tax + ", discountRate=" + discountRate
				+ ", discount=" + discount + ", sumWithTax=" + sumWithTax + ", sumWithTax2=" + sumWithTax2 + ", note="
				+ note + ", bnSwiftCode=" + bnSwiftCode + ", bnBankName=" + bnBankName + ", bnBranchName="
				+ bnBranchName + ", bnBankAddress=" + bnBankAddress + ", bnName=" + bnName + ", bnAddress=" + bnAddress
				+ ", bnAccountNumber=" + bnAccountNumber + ", bnAccountName=" + bnAccountName + ", imSwiftCode="
				+ imSwiftCode + ", imBankName=" + imBankName + ", state=" + state + ", workflowNum=" + workflowNum
				+ ", insertDate=" + insertDate + ", updateDate=" + updateDate + ", updater=" + updater + "]";
	}
	
}
