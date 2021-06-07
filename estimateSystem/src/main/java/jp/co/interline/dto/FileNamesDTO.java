package jp.co.interline.dto;

public class FileNamesDTO {
	String category;
	String fileName;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public String toString() {
		return "FileNamesDTO [category=" + category + ", fileName=" + fileName + "]";
	}
	
	
	
	
}
