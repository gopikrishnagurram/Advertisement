package com.wavelabs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wavelabs.dao.TenantDAO;
import com.wavelabs.model.Tenant;

@Service
public class TenantService {

	@Autowired
	private TenantDAO tenantDao;

	public boolean persistTenant(Tenant tenant) {

		return tenantDao.persistTenant(tenant);

	}

	public Tenant getTenant(String tenantId) {

		return tenantDao.getTenat(tenantId);
	}
}
