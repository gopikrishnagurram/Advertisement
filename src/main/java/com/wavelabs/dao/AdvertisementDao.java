package com.wavelabs.dao;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.wavelabs.model.Advertisement;
import com.wavelabs.model.User;
import com.wavelabs.utility.Helper;

@Component
public class AdvertisementDao {

	private static final Logger LOGGER = Logger.getLogger(AdvertisementDao.class);

	public Advertisement getAdvertisement(int id) {
		Session session = Helper.getSession();
		Advertisement add = (Advertisement) session.get(Advertisement.class, id);
		session.close();
		return add;
	}

	public boolean persistAdvertisement(Advertisement add) {
		Session session = Helper.getSession();
		try {
			Transaction tx = session.beginTransaction();
			session.save(add);
			tx.commit();
			return true;
		} catch (Exception e) {
			LOGGER.error(e);
			return false;
		} finally {
			session.close();
		}
	}

	public Advertisement updateAdvertisement(Advertisement add) {
		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.update(add);
			tx.commit();
			return add;
		} catch (Exception e) {
			LOGGER.error(e);
			return null;
		} finally {
			session.close();
		}
	}

	public boolean deleteAdvertisement(int id) {
		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		try {
			Advertisement add = (Advertisement) session.get(Advertisement.class, id);
			session.delete(add);
			tx.commit();
			return true;
		} catch (Exception e) {
			LOGGER.error(e);
			return false;
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Advertisement> getAllAdvertisementsOfUser(int id) {

		Session session = Helper.getSession();
		try {
			String hql = " from " + Advertisement.class.getName() + " where user=:user";
			Query query = session.createQuery(hql);
			User user = (User) session.get(User.class, id);
			query.setParameter("user", user);
			return query.list();
		} catch (Exception e) {
			LOGGER.error(e);
			return Collections.emptyList();
		} finally {
			session.close();
		}
	}
}
