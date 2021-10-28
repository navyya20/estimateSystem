package jp.co.interline.dto;

public class CompanyDTO {
	int companyInformNum;
	String companyInformName;
	String companyName;
	String representative;
	String phoneNumber;
	String address;
	String post;
	String email;
	String incharge;
	String insertDate;
	String updateDate;
	int updater;
	public int getCompanyInformNum() {
		return companyInformNum;
	}
	public void setCompanyInformNum(int companyInformNum) {
		this.companyInformNum = companyInformNum;
	}
	public String getCompanyInformName() {
		return companyInformName;
	}
	public void setCompanyInformName(String companyInformName) {
		this.companyInformName = companyInformName;
	}
	
	public String getRepresentative() {
		return representative;
	}
	public void setRepresentative(String representative) {
		this.representative = representative;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getIncharge() {
		return incharge;
	}
	public void setIncharge(String incharge) {
		this.incharge = incharge;
	}
	@Override
	public String toString() {
		return "CompanyDTO [companyInformNum=" + companyInformNum + ", companyInformName=" + companyInformName
				+ ", companyName=" + companyName + ", representative=" + representative + ", phoneNumber=" + phoneNumber
				+ ", address=" + address + ", post=" + post + ", email=" + email + ", incharge=" + incharge
				+ ", insertDate=" + insertDate + ", updateDate=" + updateDate + ", updater=" + updater + "]";
	}
	
}
