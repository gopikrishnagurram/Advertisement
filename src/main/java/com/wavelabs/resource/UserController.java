package com.wavelabs.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wavelabs.model.Message;
import com.wavelabs.model.User;
import com.wavelabs.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService service;

	@RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity persistUser(@RequestBody User user) {

		boolean flag = service.createUser(user);
		Message message = new Message();
		if (flag) {
			message.setId(201);
			message.setText("User created successfully");
			return ResponseEntity.status(HttpStatus.CREATED).body(message);
		} else {
			message.setId(500);
			message.setText("failed");
			return ResponseEntity.status(500).body(message);
		}
	}
}
