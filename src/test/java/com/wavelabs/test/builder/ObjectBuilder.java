package com.wavelabs.test.builder;

import com.wavelabs.dao.AdvertisementDao;
import com.wavelabs.dao.CommentDAO;
import com.wavelabs.dao.UserAdvertIntrestedDao;
import com.wavelabs.dao.UserDAO;

public class ObjectBuilder {

	private static AdvertisementDao addDao;

	private static CommentDAO commentDao;

	private static UserAdvertIntrestedDao uaio;

	private static UserDAO userDAO;

	public static AdvertisementDao getAdvertismentDao() {

		if (addDao == null) {
			addDao = new AdvertisementDao();
		}
		return addDao;
	}

	public static CommentDAO getCommentDao() {

		if (commentDao == null) {
			commentDao = new CommentDAO();
		}

		return commentDao;

	}

	public static UserAdvertIntrestedDao getUserAdvertDao() {

		if (uaio == null) {

			uaio = new UserAdvertIntrestedDao();

		}
		return uaio;
	}

	public static UserDAO getUserDAO() {

		if (userDAO == null) {

			userDAO = new UserDAO();
		}

		return userDAO;

	}

}
