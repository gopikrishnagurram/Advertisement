package com.wavelabs.multithread;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.wavelabs.confirmation.service.MailConfirmation;
import com.wavelabs.confirmation.service.PhoneConfirmation;
import com.wavelabs.model.Advertisement;
import com.wavelabs.model.User;

public class ConfirmationThread implements Runnable {

	private Advertisement add;
	private User user;
	private String type;
	private static final Logger LOGGER = Logger.getLogger(ConfirmationThread.class); 
	public ConfirmationThread(Advertisement add, User user, String type) {

		this.add = add;
		this.user = user;
		this.type = type;
	}
	

	@Override
	public void run() {
		synchronized (this) {
			try {
				MailConfirmation.sendMail(add, user, type);
			} catch (IOException e) {
				LOGGER.error(e);
			}
			PhoneConfirmation.sendPhoneConfirmation(add, user, type);
			shutDown();
		}

	}

	@SuppressWarnings("deprecation")
	private void shutDown() {
		Thread.currentThread().stop();
	}
}
