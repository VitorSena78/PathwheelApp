package com.myapplication.api.model;

public class User {
	private Long id;
	private String fullName;
	private String userName;
	private String email;
	private String registrationDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + fullName + ", user_name=" + userName + ", email=" + email
				+ ", registrationDate=" + registrationDate + "]";
	}
	
}
