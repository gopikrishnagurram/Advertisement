package com.wavelabs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wavelabs.dao.UserDAO;
import com.wavelabs.model.User;

@Service
public class UserService {

	@Autowired
	private UserDAO userDao;

	public boolean createUser(User user) {
		return userDao.createUser(user);
	}
	
	
	

}
