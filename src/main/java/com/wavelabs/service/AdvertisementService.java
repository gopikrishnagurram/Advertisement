package com.wavelabs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wavelabs.dao.AdvertisementDao;
import com.wavelabs.model.Advertisement;
import com.wavelabs.multithread.ConfirmationThread;

@Service
public class AdvertisementService {

	@Autowired
	private AdvertisementDao addDao;

	public AdvertisementService(AdvertisementDao addDao) {

		this.addDao = addDao;
	}

	public boolean persistAdvertisement(Advertisement add) {

		boolean flag = addDao.persistAdvertisement(add);
		if (flag) {
			ConfirmationThread confirmationThread = new ConfirmationThread(add, add.getUser(), "POST");
			Thread thread = new Thread(confirmationThread);
			thread.start();
		}
		return flag;
	}

	public Advertisement getAdvertisement(int id) {

		return addDao.getAdvertisement(id);
	}

	public Advertisement updateAdvertisement(Advertisement add) {

		return addDao.updateAdvertisement(add);
	}

	public boolean deleteAdvertisement(int id) {
		return addDao.deleteAdvertisement(id);
	}

	public List<Advertisement> getAllAdvertisementOfUser(int id) {

		return addDao.getAllAdvertisementsOfUser(id);
	}

}
