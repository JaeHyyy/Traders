package com.exam.security;

// token 저장 기능
public class JwtTokenResponse {

	String token;

	public JwtTokenResponse() {}

	public JwtTokenResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "JwtTokenResponse [token=" + token + "]";
	}
	
}
