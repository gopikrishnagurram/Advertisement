package com.wavelabs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wavelabs.dao.CommentDAO;
import com.wavelabs.model.Comment;

@Service
public class CommentService {

	@Autowired
	private CommentDAO commentDao;

	public CommentService(CommentDAO commentDao) {
		this.commentDao = commentDao;
	}

	public boolean persistComment(Comment comment) {

		return commentDao.persistComment(comment);

	}

	public Comment[] getListOfCommentsOfAdvertisement(int id) {

		return commentDao.getAdvertisementComments(id);
	}

	public Comment updateComment(Comment comment) {

		return commentDao.updateComment(comment);
	}

	public Boolean deleteComment(int id) {
		return commentDao.deleteComment(id);
	}
	
}
