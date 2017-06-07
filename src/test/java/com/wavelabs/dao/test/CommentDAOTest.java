package com.wavelabs.dao.test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.hql.internal.ast.QuerySyntaxException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.wavelabs.dao.CommentDAO;
import com.wavelabs.model.Advertisement;
import com.wavelabs.model.Comment;
import com.wavelabs.test.builder.CommentBuilder;
import com.wavelabs.test.builder.ObjectBuilder;
import com.wavelabs.utility.Helper;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Helper.class)
public class CommentDAOTest {

	private Session session;

	@Before
	public void setUp() {
		session = mock(Session.class);
		PowerMockito.mockStatic(Helper.class);
		when(Helper.getSession()).thenReturn(session);
	}

	@Test
	public void testPersistComment1() {

		Session session = mock(Session.class);
		Comment comment = mock(Comment.class);
		when(Helper.getSession()).thenReturn(session);
		when(session.save(comment)).thenReturn(111);
		Transaction tx = mock(Transaction.class);
		when(session.beginTransaction()).thenReturn(tx);
		boolean flag = ObjectBuilder.getCommentDao().persistComment(comment);
		Assert.assertEquals(true, flag);
	}

	@Test
	public void testPersistComment2() {

		Session session = mock(Session.class, RETURNS_DEEP_STUBS);
		Comment comment = mock(Comment.class);
		when(Helper.getSession()).thenReturn(session);
		when(session.save(comment)).thenReturn(111);
		Transaction tx = mock(Transaction.class);
		PowerMockito.doThrow(new TransactionException("fake exception")).when(tx).commit();
		when(session.beginTransaction()).thenReturn(tx);
		boolean flag = ObjectBuilder.getCommentDao().persistComment(comment);
		Assert.assertEquals(false, flag);
	}

	@Test
	public void testGetListOfComments1() {

		Session session = mock(Session.class);
		Advertisement add = mock(Advertisement.class);
		when(Helper.getSession()).thenReturn(session);
		List<Comment> commentList = new ArrayList<>();
		commentList.add(CommentBuilder.buildComment());
		commentList.add(CommentBuilder.buildComment());
		when(session.get(eq(Advertisement.class), any())).thenReturn(add);
		Query query = mock(Query.class);
		when(session.createQuery(anyString())).thenReturn(query);
		when(query.list()).thenReturn(commentList);
		Comment[] array = ObjectBuilder.getCommentDao().getAdvertisementComments(2);
		Assert.assertEquals(2, array.length);
	}

	@Test
	public void testGetListOfComments2() {

		Session session = mock(Session.class);
		Advertisement add = mock(Advertisement.class);
		when(Helper.getSession()).thenReturn(session);
		List<Comment> commentList = new ArrayList<>();
		commentList.add(CommentBuilder.buildComment());
		commentList.add(CommentBuilder.buildComment());
		when(session.get(eq(Advertisement.class), any())).thenReturn(add);
		Query query = mock(Query.class);
		PowerMockito.doThrow(new QuerySyntaxException("Fake syntax exception message")).when(session)
				.createQuery(anyString());
		when(query.list()).thenReturn(commentList);
		Comment[] array = ObjectBuilder.getCommentDao().getAdvertisementComments(2);
		Assert.assertEquals(0, array.length);
	}

	@Test
	public void testUpdateComment1() {

		when(Helper.getSession()).thenReturn(session);
		Comment comment = CommentBuilder.buildComment();
		PowerMockito.doNothing().when(session).update(any(Comment.class));
		Transaction tx = mock(Transaction.class);
		when(session.beginTransaction()).thenReturn(tx);
		Comment newComment = ObjectBuilder.getCommentDao().updateComment(comment);
		Assert.assertEquals(comment.getText(), newComment.getText());
	}
	@Test
	public void testUpdateComment2() {

		when(Helper.getSession()).thenReturn(session);
		Comment comment = CommentBuilder.buildComment();
		PowerMockito.doNothing().when(session).update(any(Comment.class));
		Transaction tx = mock(Transaction.class);
		Comment newComment = ObjectBuilder.getCommentDao().updateComment(comment);
		Assert.assertEquals(null, newComment);
	}
	
	
	@Test
	public void deleteComment1() {
		
		Comment comment = CommentBuilder.buildComment();
		when(Helper.getSession()).thenReturn(session);
		when(session.get(eq(Comment.class), any())).thenReturn(comment);
		PowerMockito.doNothing().when(session).delete(any(Comment.class));
		Transaction tx = mock(Transaction.class);
		when(session.beginTransaction()).thenReturn(tx);
		boolean flag = ObjectBuilder.getCommentDao().deleteComment(1);
		Assert.assertEquals(true, flag);
	}

	@Test
	public void deleteComment2() {
		
		Comment comment = CommentBuilder.buildComment();
		when(Helper.getSession()).thenReturn(session);
		when(session.get(eq(Comment.class), any())).thenReturn(comment);
		PowerMockito.doNothing().when(session).delete(any(Comment.class));
		boolean flag = ObjectBuilder.getCommentDao().deleteComment(1);
		Assert.assertEquals(false, flag);
	}
}
