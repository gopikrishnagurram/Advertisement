package com.wavelabs.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wavelabs.model.Tenant;
import com.wavelabs.model.User;
import com.wavelabs.service.TenantService;
import com.wavelabs.service.UserService;

/**
 * 
 * @author gopikrishnag
 */
@Component
public class Persister {

	@Autowired
	private TenantService tenantService;

	@Autowired
	private UserService userService;

	public boolean persistTenant(Tenant tenant) {

		return tenantService.persistTenant(tenant);
	}

	public boolean persistUser(User user) {

		return userService.createUser(user);
	}
}
