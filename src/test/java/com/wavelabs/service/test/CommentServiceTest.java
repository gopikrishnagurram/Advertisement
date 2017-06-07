package com.wavelabs.service.test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gson.Gson;
import com.wavelabs.dao.CommentDAO;
import com.wavelabs.model.Comment;
import com.wavelabs.service.CommentService;
import com.wavelabs.test.builder.CommentBuilder;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

	@Mock
	private CommentDAO commentDao;

	@InjectMocks
	private CommentService service;

	@Test
	public void testPersistComment() {

		Comment comment = CommentBuilder.buildComment();
		when(commentDao.persistComment(any(Comment.class))).thenReturn(true);
		boolean flag = service.persistComment(comment);
		Assert.assertEquals(true, flag);
	}

	@Test
	public void testGetComment() {

		Comment[] comment = { CommentBuilder.buildComment() };
		when(commentDao.getAdvertisementComments(anyInt())).thenReturn(comment);
		Comment[] newComments = service.getListOfCommentsOfAdvertisement(2);
		Assert.assertEquals(comment.length, newComments.length);
	}

	@Test
	public void testUpdateComment() {

		Comment comment = CommentBuilder.buildComment();
		when(commentDao.updateComment(any(Comment.class))).thenReturn(comment);
		Comment newComment = service.updateComment(comment);
		Gson gson = new Gson();
		Assert.assertEquals(gson.toJson(comment), gson.toJson(newComment));
	}

	@Test
	public void testDeleteAdvertisement() {

		when(commentDao.deleteComment(anyInt())).thenReturn(true);
		boolean flag = service.deleteComment(2);
		Assert.assertEquals(true, flag);
	}
}
