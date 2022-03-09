package jp.co.interline.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class jacksonUtility {
	public static ObjectMapper Mapper = new ObjectMapper(); 
	
	public static <T> T readValue(String jsonStr, Class<T> type) {
		try {
			return Mapper.readValue(jsonStr,type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
