package com.fronchak.movie_flix_spring3.dtos.user;

import java.io.Serializable;

public class TokenOutputDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;
	
	public TokenOutputDTO() {}
	
	public TokenOutputDTO(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
