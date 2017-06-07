package com.wavelabs.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.wavelabs.model.User;
import com.wavelabs.model.UserAdvertIntrested;
import com.wavelabs.utility.Helper;

@Component
public class UserAdvertIntrestedDao {

	private static final Logger LOGGER = Logger.getLogger(UserAdvertIntrestedDao.class);

	public boolean persistUserAdverIntrested(UserAdvertIntrested uai) {
		Session session = Helper.getSession();

		try {
			Transaction tx = session.beginTransaction();
			session.save(uai);
			tx.commit();
			return true;
		} catch (Exception e) {
			LOGGER.error(e);
			return false;
		} finally {
			session.close();
		}
	}

	public UserAdvertIntrested get(int id) {
		try {
			Session session = Helper.getSession();
			return (UserAdvertIntrested) session.get(UserAdvertIntrested.class, id);
		} catch (Exception e) {
			LOGGER.error(e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<UserAdvertIntrested> getUserAdvertIntrested(int id) {
		Session session = Helper.getSession();
		String hql = "from " + UserAdvertIntrested.class.getName() + " where user=:user";
		User user = (User) session.get(User.class, id);
		Query query = session.createQuery(hql);
		query.setParameter("user", user);
		return query.list();
	}

}
