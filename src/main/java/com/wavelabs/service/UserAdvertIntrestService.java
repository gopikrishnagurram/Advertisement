package com.wavelabs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wavelabs.dao.UserAdvertIntrestedDao;
import com.wavelabs.model.UserAdvertIntrested;
import com.wavelabs.multithread.ConfirmationThread;

@Service
public class UserAdvertIntrestService {

	@Autowired
	private UserAdvertIntrestedDao uao;

	public UserAdvertIntrestService(UserAdvertIntrestedDao uao) {

		this.uao = uao;
	}

	public boolean persistUserAdvert(UserAdvertIntrested uia) {
		boolean flag = uao.persistUserAdverIntrested(uia);
		if (flag) {
			ConfirmationThread confirmationThread = new ConfirmationThread(uia.getAdvert(), uia.getUser(), "APPLY");
			Thread thread = new Thread(confirmationThread);
			thread.start();
		}
		return flag;
	}

	public UserAdvertIntrested getUserAdvert(int id) {

		return uao.get(id);
	}

	public List<UserAdvertIntrested> getAllUserAdvertIntrests(int id) {

		return uao.getUserAdvertIntrested(id);
	}
}
