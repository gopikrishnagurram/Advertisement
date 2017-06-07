package com.wavelabs.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wavelabs.model.Comment;
import com.wavelabs.model.Message;
import com.wavelabs.service.CommentService;

@RestController
public class CommentResource {

	@Autowired
	private CommentService commentService;

	private CommentResource(CommentService commentService) {
		this.commentService = commentService;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("advertisement/comment/{id}")
	public ResponseEntity getComment(@PathVariable("id") int id) {

		Comment[] comment = commentService.getListOfCommentsOfAdvertisement(id);
		if (comment != null) {
			return ResponseEntity.status(200).body(comment);
		} else {

			Message message = new Message();
			message.setId(404);
			message.setText("Comments not found");
			return ResponseEntity.status(404).body(message);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/advertisement/comment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity saveComment(@RequestBody Comment comment) {

		boolean flag = commentService.persistComment(comment);
		Message message = new Message();
		if (flag) {
			message.setId(200);
			message.setText("Comment created successfully");
			return ResponseEntity.status(201).body(message);
		} else {
			message.setId(500);
			message.setText("Comment creation failed due to server error");
			return ResponseEntity.status(500).body(message);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/advertisement/comment/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity updateComment(@PathVariable("id") int id, @RequestBody Comment comment) {
		comment.setId(id);
		Comment newComment = commentService.updateComment(comment);
		if (newComment != null) {
			return ResponseEntity.status(200).body(newComment);
		} else {
			Message message = new Message();
			message.setId(500);
			message.setText("Updation failed due to server error");
			return ResponseEntity.status(500).body(message);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/advertisement/comment/{id}", method = RequestMethod.DELETE)
	public ResponseEntity deleteComment(@PathVariable("id") int id) {

		boolean flag = commentService.deleteComment(id);
		Message message = new Message();
		if (flag) {

			message.setId(200);
			message.setText("Comment deleted successfully");
			return ResponseEntity.status(200).body(message);
		} else {

			message.setId(500);
			message.setText("Server error");
			return ResponseEntity.status(500).body(message);
		}
	}
}
