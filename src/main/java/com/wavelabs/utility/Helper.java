package com.wavelabs.utility;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.google.gson.Gson;
import com.wavelabs.model.Tenant;
import com.wavelabs.model.User;

public class Helper {

	private Helper() {

	}

	private static Session session = null;
	private static SessionFactory factory = null;
	private static int count;
	private static Configuration cfg = null;

	public static Session getSession() {
		if (count == 0) {
			setFactory();
		}
		if (!session.isOpen()) {
			session = factory.openSession();
		}
		return session;
	}

	public static SessionFactory getFactory() {
		if (count == 0) {
			setFactory();
		}
		return factory;
	}

	@SuppressWarnings("deprecation")
	private static void setFactory() {
		cfg = new Configuration().configure();
		/*cfg = cfg.setProperty("hibernate.connection.url", System.getenv("database.url"));*/
		factory = cfg.buildSessionFactory();
		session = factory.openSession();
		count++;
	}
	
	public static void main(String[] args) {
		Session session = getSession();
	}
}
