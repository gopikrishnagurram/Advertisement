package com.wavelabs.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.wavelabs.model.User;
import com.wavelabs.utility.Helper;

@Component
public class UserDAO {

	private static final Logger logger = Logger.getLogger(UserDAO.class);

	/* this is to get usr */
	public User getUser(int id) {
		Session session = Helper.getSession();
		try {
			return ((User) (session.get(User.class, id)));
		} catch (Exception e) {
			logger.error(e);
			return null;
		} finally {
			session.close();
		}
	}

	public boolean createUser(User user) {

		Session session = Helper.getSession();
		try {
			Transaction tx = session.beginTransaction();
			session.save(user);
			tx.commit();
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		} finally {
			session.close();
		}
	}
}
