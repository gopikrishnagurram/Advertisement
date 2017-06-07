package com.wavelabs.record.existence;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import com.wavelabs.model.Tenant;
import com.wavelabs.model.User;
import com.wavelabs.utility.Helper;

@Component
public class ObjectChecker {

	private static final Logger logger = Logger.getLogger(ObjectChecker.class);

	Session session = null;

	public boolean isUserExist(String userName, String tenantId) {
		session = Helper.getSession();
		try {
			Tenant tenant = getTenant(tenantId);
			if(!session.isOpen()){
				session=Helper.getSession();
			}
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("tenantUserName", userName));
			cr.add(Restrictions.eq("tenant", tenant));
			User user = (User) cr.uniqueResult();
			return (user != null);
		} catch (Exception e) {
			logger.error(e);
			return false;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
	}

	public boolean isTenantExist(String tenantId) {
		try {
			if(getTenant(tenantId) == null) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	public Tenant getTenant(String tenantId) {

		session = Helper.getSession();
		try {
			Criteria cr = session.createCriteria(Tenant.class);
			cr = cr.add(Restrictions.eq("tenantId", tenantId));
			return (Tenant) cr.uniqueResult();
		} catch (Exception e) {
			logger.error(e);
			return null;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
	}
}
