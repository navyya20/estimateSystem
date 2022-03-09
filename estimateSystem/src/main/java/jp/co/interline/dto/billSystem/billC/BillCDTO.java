package jp.co.interline.dto.billSystem.billC;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.interline.dto.billSystem.BillCommonDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillCDTO extends BillCommonDTO{
	String documentNum;
	String estimateNum;
	int userNum;
	String userName;
	String userDepartment;
	String userPosition;
	String billDate;
	String documentTypeName;
	String nextDocumentTypeName;
	
	String stamp;
	String stampFileName;
	String logoFileName;
	
	String receiver;
	String address;
	String documentName;
	String payCondition;
	String deadline;
	String itemTitle;
	
	@JsonProperty("items")
	ArrayList<BillCItemDTO> items;
	
	String bankName;
	String branchName;
	String accountName;
	String hurigana;
	String accountNumber;
	String depositeClassification;	
	String note;
	String sum;
	String taxRate;
	String tax;
	String commissionRate;
	String commission;
	String sumWithTax;
	String sumWithTax2;	
	String state;
	int workflowNum;
	String insertDate;
	String updateDate;
	int updater;
	int systemNum;
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
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getPayCondition() {
		return payCondition;
	}
	public void setPayCondition(String payCondition) {
		this.payCondition = payCondition;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getHurigana() {
		return hurigana;
	}
	public void setHurigana(String hurigana) {
		this.hurigana = hurigana;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getDepositeClassification() {
		return depositeClassification;
	}
	public void setDepositeClassification(String depositeClassification) {
		this.depositeClassification = depositeClassification;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public int getSystemNum() {
		return systemNum;
	}
	public void setSystemNum(int systemNum) {
		this.systemNum = systemNum;
	}
	public String getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
	public String getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(String commissionRate) {
		this.commissionRate = commissionRate;
	}
	public String getCommission() {
		return commission;
	}
	public void setCommission(String commission) {
		this.commission = commission;
	}
	public ArrayList<BillCItemDTO> getItems() {
		return items;
	}
	public void setItems(ArrayList<BillCItemDTO> items) {
		this.items = items;
	}
	@Override
	public String toString() {
		return "BillCDTO [documentNum=" + documentNum + ", estimateNum=" + estimateNum + ", userNum=" + userNum
				+ ", userName=" + userName + ", userDepartment=" + userDepartment + ", userPosition=" + userPosition
				+ ", billDate=" + billDate + ", documentTypeName=" + documentTypeName + ", nextDocumentTypeName="
				+ nextDocumentTypeName + ", stamp=" + stamp + ", stampFileName=" + stampFileName + ", logoFileName="
				+ logoFileName + ", receiver=" + receiver + ", address=" + address + ", documentName=" + documentName
				+ ", payCondition=" + payCondition + ", deadline=" + deadline + ", itemTitle=" + itemTitle + ", items="
				+ items + ", bankName=" + bankName + ", branchName=" + branchName + ", accountName=" + accountName
				+ ", hurigana=" + hurigana + ", accountNumber=" + accountNumber + ", depositeClassification="
				+ depositeClassification + ", note=" + note + ", sum=" + sum + ", taxRate=" + taxRate + ", tax=" + tax
				+ ", commissionRate=" + commissionRate + ", commission=" + commission + ", sumWithTax=" + sumWithTax
				+ ", sumWithTax2=" + sumWithTax2 + ", state=" + state + ", workflowNum=" + workflowNum + ", insertDate="
				+ insertDate + ", updateDate=" + updateDate + ", updater=" + updater + ", systemNum=" + systemNum + "]";
	}
	
	
	
}
