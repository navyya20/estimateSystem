package jp.co.interline.dto;

public class DepartmentDTO {
	int departmentNum;
	String department;
	public int getDepartmentNum() {
		return departmentNum;
	}
	public void setDepartmentNum(int departmentNum) {
		this.departmentNum = departmentNum;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	@Override
	public String toString() {
		return "departmentDTO [departmentNum=" + departmentNum + ", department=" + department + "]";
	}
	
	
}
