package com.wavelabs.model;

public class User {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String tenantUserName;
	private Tenant tenant;
	public User(int id, String firstName, String email, String phone) {

		this.id = id;
		this.firstName = firstName;
		this.email = email;
		this.phone = phone;

	}

	public User() {

	}

	public String getTenantUserName() {
		return tenantUserName;
	}

	public void setTenantUserName(String tenantUserName) {
		this.tenantUserName = tenantUserName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
}
