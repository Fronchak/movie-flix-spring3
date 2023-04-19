package com.fronchak.movie_flix_spring3.dtos.user;

import java.util.List;

public class UserInputAuxDTO {

	private String email;
	private String password;
	private List<Long> idRoles;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Long> getIdRoles() {
		return idRoles;
	}
	
	public void setIdRoles(List<Long> idRoles) {
		this.idRoles = idRoles;
	}
}
