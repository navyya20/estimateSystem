package jp.co.interline.dto;

public class AccountDTO {
	int accountInformNum;
	String accountInformName;
	String bankName;
	String branchName;
	String accountName;
	String hurigana;
	String accountNumber;
	String depositeClassification;
	String insertDate;
	String updateDate;
	int updater;
	public int getAccountInformNum() {
		return accountInformNum;
	}
	public void setAccountInformNum(int accountInformNum) {
		this.accountInformNum = accountInformNum;
	}
	public String getAccountInformName() {
		return accountInformName;
	}
	public void setAccountInformName(String accountInformName) {
		this.accountInformName = accountInformName;
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
	@Override
	public String toString() {
		return "AccountDTO [accountInformNum=" + accountInformNum + ", accountInformName=" + accountInformName
				+ ", bankName=" + bankName + ", branchName=" + branchName + ", accountName=" + accountName
				+ ", hurigana=" + hurigana + ", accountNumber=" + accountNumber + ", depositeClassification="
				+ depositeClassification + ", insertDate=" + insertDate + ", updateDate=" + updateDate + ", updater="
				+ updater + "]";
	}
	
	
}
