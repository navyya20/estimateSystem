package jp.co.interline.service;
import java.util.Properties;


import org.springframework.core.io.ClassPathResource;


public class GetProperties {
	
	String webIP="";
	String ozIP="";
	String projectRoot="";
	public GetProperties() {
		ClassPathResource resource = new ClassPathResource("user.properties");
		
		
		Properties properties = new Properties();
	   
	    try {
		    properties.load(resource.getInputStream());
		} catch (Exception e) {
			System.out.println(e);
		}
	    webIP=properties.getProperty("webIP","localhost:8888");
	    ozIP=properties.getProperty("ozIP","localhost:8888");
	    projectRoot=properties.getProperty("projectRoot","estimateSystem");
	}


	public String getWebIP() {
		return webIP;
	}
	public void setWebIP(String webIP) {
		this.webIP = webIP;
	}
	
	public String getOzIP() {
		return ozIP;
	}
	public void setOzIP(String ozIP) {
		this.ozIP = ozIP;
	}
	public String getProjectRoot() {
		return projectRoot;
	}
	public void setProjectRoot(String projectRoot) {
		this.projectRoot = projectRoot;
	}


	@Override
	public String toString() {
		return "getProperties [webIP=" + webIP + ", ozIP=" + ozIP + "]";
	}
	    
	
}
