package com.example.demo.entity;

public class JwtInfo {
	private String token;
	private String name;

	public JwtInfo(String token, String name) {
		super();
		this.token = token;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
