package com.wavelabs.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.wavelabs.model.Tenant;
import com.wavelabs.utility.Helper;
@Component
public class TenantDAO {
	public boolean persistTenant(Tenant tenant) {
		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		session.save(tenant);
		tx.commit();
		session.close();
		return true;
	}

	public Tenant getTenat(String tenantId) {
		Session session = Helper.getSession();
		Criteria cr = session.createCriteria(Tenant.class);
		cr = cr.add(Restrictions.eq("tenantId", tenantId));
		session.close();
		return (Tenant) cr.uniqueResult();
	}
}