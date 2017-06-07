package com.wavelabs.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wavelabs.model.Message;
import com.wavelabs.model.UserAdvertIntrested;
import com.wavelabs.service.UserAdvertIntrestService;

@RestController
public class UserAdvertIntrestedResource {

	@Autowired
	private UserAdvertIntrestService service;

	public UserAdvertIntrestedResource(UserAdvertIntrestService service) {
		this.service = service;
	}

	@RequestMapping("/user/interest/{id}")
	public ResponseEntity getUserAdvertIntrest(@PathVariable("id") int id) {

		UserAdvertIntrested uai = service.getUserAdvert(id);
		if (uai != null) {
			return ResponseEntity.status(200).body(uai);
		} else {

			Message message = new Message();
			message.setId(404);
			message.setText("Not found");
			return ResponseEntity.status(404).body(message);
		}
	}

	@RequestMapping(value = "/user/interest/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity postUserAdvertIntrest(@RequestBody UserAdvertIntrested uai) {

		boolean flag = service.persistUserAdvert(uai);
		Message message = new Message();
		if (flag) {
			message.setId(200);
			message.setText("Saved");
			return ResponseEntity.status(200).body(message);
		} else {
			message.setId(500);
			message.setText("failed");
			return ResponseEntity.status(500).body(message);
		}
	}

	@RequestMapping(value = "/user/interest/{id}", method = RequestMethod.DELETE)
	public ResponseEntity getUserIntrests(@PathVariable("id") int id) {
		List<UserAdvertIntrested> intrests = service.getAllUserAdvertIntrests(id);
		Message message = new Message();
		if (!intrests.isEmpty()) {
			message.setId(200);
			message.setText("deleted");
			return ResponseEntity.status(200).body(message);
		} else {
			message.setId(500);
			message.setText("failed");
			return ResponseEntity.status(500).body(message);
		}

	}
}
