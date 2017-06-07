package com.wavelabs.controller.test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;
import com.wavelabs.model.Comment;
import com.wavelabs.resource.CommentResource;
import com.wavelabs.service.AdvertisementService;
import com.wavelabs.service.CommentService;
import com.wavelabs.test.builder.CommentBuilder;

@RunWith(MockitoJUnitRunner.class)
public class CommentControllerTest {

	@Mock
	private CommentService commentService;

	@InjectMocks
	private CommentResource commentResource;

	@Test
	public void testPersistComment() throws Exception {
		Comment comment = CommentBuilder.buildComment();
		when(commentService.persistComment(any(Comment.class))).thenReturn(true);
		ResponseEntity entity = commentResource.saveComment(comment);
		Assert.assertEquals(201, entity.getStatusCodeValue());
	}

	@Test
	public void testPersistComment2() throws Exception {
		Comment comment = CommentBuilder.buildComment();
		when(commentService.persistComment(any(Comment.class))).thenReturn(false);
		ResponseEntity entity = commentResource.saveComment(comment);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@Test
	public void testGetComment1() throws Exception {
		Comment[] comment = { CommentBuilder.buildComment(), CommentBuilder.buildComment() };
		when(commentService.getListOfCommentsOfAdvertisement(anyInt())).thenReturn(comment);
		ResponseEntity entity = commentResource.getComment(2);
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testGetComment2() throws Exception {
		when(commentService.getListOfCommentsOfAdvertisement(anyInt())).thenReturn(null);
		ResponseEntity entity = commentResource.getComment(1);
		Assert.assertEquals(404, entity.getStatusCodeValue());
	}

	@Test
	public void testUpdateComment1() throws Exception {

		Comment comment = CommentBuilder.buildComment();
		when(commentService.updateComment(any(Comment.class))).thenReturn(comment);
		ResponseEntity entity = commentResource.updateComment(comment.getId(), comment);
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testUpdateComment2() throws Exception {

		Comment comment = CommentBuilder.buildComment();
		Gson gson = new Gson();
		when(commentService.updateComment(comment)).thenReturn(null);
		ResponseEntity entity = commentResource.updateComment(comment.getId(), comment);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@Test
	public void testDeleteComment1() throws Exception {
		when(commentService.deleteComment(anyInt())).thenReturn(true);
		ResponseEntity entity = commentResource.deleteComment(2);
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testDeleteComment2() throws Exception {
		when(commentService.deleteComment(anyInt())).thenReturn(false);
		ResponseEntity entity = commentResource.deleteComment(2);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

}
