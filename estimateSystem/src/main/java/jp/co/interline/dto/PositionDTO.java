package jp.co.interline.dto;

public class PositionDTO {
	int positionNum;
	String position;
	public int getPositionNum() {
		return positionNum;
	}
	public void setPositionNum(int positionNum) {
		this.positionNum = positionNum;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	@Override
	public String toString() {
		return "positionDTO [positionNum=" + positionNum + ", position=" + position + "]";
	}
	
	
}
