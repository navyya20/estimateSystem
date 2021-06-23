package jp.co.interline.dto;

import java.util.ArrayList;
import java.util.HashMap;

public class testDTO {
	String cola = "1stcol";
	String colb = "2stcol";
	String datea;
	String dbName = "testTable";
	public String getCola() {
		return cola;
	}
	public void setCola(String cola) {
		this.cola = cola;
	}
	public String getColb() {
		return colb;
	}
	public void setColb(String colb) {
		this.colb = colb;
	}
	public String getDatea() {
		return datea;
	}
	public void setDatea(String datea) {
		this.datea = datea;
	}
	
	
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	
	@Override
	public String toString() {
		return "testDTO [cola=" + cola + ", colb=" + colb + ", datea=" + datea + ", dbName=" + dbName + "]";
	}
	public ArrayList<HashMap<String, String>> getHashMapList(){
		ArrayList<HashMap<String, String>> arr= new ArrayList<>();
		HashMap<String, String> h1 = new HashMap<>();
		h1.put("dbName", this.getDbName());
		arr.add(0, h1);
		HashMap<String, String> h2 = new HashMap<>();
		h2.put("colName", "cola");
		h2.put("colVal", this.getCola());
		arr.add(1, h2);
		HashMap<String, String> h3 = new HashMap<>();
		h3.put("colName", "colb");
		h3.put("colVal", this.getColb());
		arr.add(2, h3);
		HashMap<String, String> h4 = new HashMap<>();
		h4.put("colName", "datea");
		h4.put("colVal", "CURRENT_TIMESTAMP");
		arr.add(3, h4);
		return arr;
	}
}
