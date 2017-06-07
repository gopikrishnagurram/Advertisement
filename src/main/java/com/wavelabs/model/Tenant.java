package com.wavelabs.model;

import java.util.Calendar;

public class Tenant {
	private int id;
	private String clientId;
	private Calendar expirationTime;
	private boolean expired;
	private String tenantId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Calendar getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Calendar expirationTime) {
		this.expirationTime = expirationTime;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
