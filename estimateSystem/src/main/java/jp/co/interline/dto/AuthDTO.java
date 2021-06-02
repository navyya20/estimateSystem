package jp.co.interline.dto;

public class AuthDTO {
	String auth;
	String explanation;
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	@Override
	public String toString() {
		return "AuthDTO [auth=" + auth + ", explanation=" + explanation + "]";
	}
	
	
}
