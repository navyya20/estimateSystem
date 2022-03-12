package jp.co.interline.dto.estimateSystem.estimateLanguage;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.interline.dto.estimateSystem.EstimateCommonDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EstimateLanguageDTO extends EstimateCommonDTO{
	String estimateDate;
	String documentTypeName;
	String nextDocumentTypeName;
	String stamp;
	
	String address;
	String deadline;
	String expirationDate;
	String payCondition;
	
	@JsonProperty("items")
	ArrayList<EstimateLanguageItemDTO> items;
	
	String bankName;
	String branchName;
	String accountName;
	String hurigana;
	String accountNumber;
	String depositeClassification;
	String cautions;
	String sum;
	String discountName;
	String discountRate;
	String discount;
	String taxRate;
	String tax;
	String sumWithTax;
	String sumWithTax2;	
	
	String comment;
	String insertDate;
	
	public String getEstimateDate() {
		return estimateDate;
	}
	public void setEstimateDate(String estimateDate) {
		this.estimateDate = estimateDate;
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
	public String getStamp() {
		return stamp;
	}
	public void setStamp(String stamp) {
		this.stamp = stamp;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getPayCondition() {
		return payCondition;
	}
	public void setPayCondition(String payCondition) {
		this.payCondition = payCondition;
	}
	public ArrayList<EstimateLanguageItemDTO> getItems() {
		return items;
	}
	public void setItems(ArrayList<EstimateLanguageItemDTO> items) {
		this.items = items;
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
	public String getCautions() {
		return cautions;
	}
	public void setCautions(String cautions) {
		this.cautions = cautions;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public String getDiscountName() {
		return discountName;
	}
	public void setDiscountName(String discountName) {
		this.discountName = discountName;
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	
}
