package jp.co.interline.dto;

public class FileNamesDTO {
	int fileNamesNum;
	String stampFileName;
	String logoFileName;
	public int getFileNamesNum() {
		return fileNamesNum;
	}
	public void setFileNamesNum(int fileNamesNum) {
		this.fileNamesNum = fileNamesNum;
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
	@Override
	public String toString() {
		return "FileNamesDTO [fileNamesNum=" + fileNamesNum + ", stampFileName=" + stampFileName + ", logoFileName="
				+ logoFileName + "]";
	}
	
	
}
