package jp.co.interline.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class jacksonUtility {
	public static ObjectMapper Mapper = new ObjectMapper(); 
	
	public static <T> T readValue(String jsonStr, Class<T> type) throws JsonMappingException, JsonProcessingException {
		return Mapper.readValue(jsonStr,type);
		
	}
}
