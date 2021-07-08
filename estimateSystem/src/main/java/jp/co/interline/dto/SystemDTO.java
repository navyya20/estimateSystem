package jp.co.interline.dto;

public class SystemDTO {
	int seqNum;
	
	//fileName
	String category;
	String fileName;
	String logoFileName;
	String stampFileName;
	
	//documentState
	String state;
	String name;
	int userNum;
	
	//documentType
	String documentTypeName;
	String explanation;
	int pair1;
	int pair2;
	int systemNum;
	String systemName;
	
	String documentNum;
	
	public int getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}

	
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

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


	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public int getPair1() {
		return pair1;
	}

	public void setPair1(int pair1) {
		this.pair1 = pair1;
	}

	public int getPair2() {
		return pair2;
	}

	public void setPair2(int pair2) {
		this.pair2 = pair2;
	}

	public int getSystemNum() {
		return systemNum;
	}

	public void setSystemNum(int systemNum) {
		this.systemNum = systemNum;
	}

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}

	public String getStampFileName() {
		return stampFileName;
	}

	public void setStampFileName(String stampFileName) {
		this.stampFileName = stampFileName;
	}

	public String getDocumentNum() {
		return documentNum;
	}

	public void setDocumentNum(String documentNum) {
		this.documentNum = documentNum;
	}

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	@Override
	public String toString() {
		return "SystemDTO [seqNum=" + seqNum + ", category=" + category + ", fileName=" + fileName + ", logoFileName="
				+ logoFileName + ", stampFileName=" + stampFileName + ", state=" + state + ", name=" + name
				+ ", documentTypeName=" + documentTypeName + ", explanation=" + explanation + ", pair1=" + pair1
				+ ", pair2=" + pair2 + ", systemNum=" + systemNum + ", documentNum=" + documentNum + "]";
	}
	
	
}
