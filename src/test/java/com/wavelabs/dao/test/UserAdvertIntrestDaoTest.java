package com.wavelabs.dao.test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.Gson;
import com.wavelabs.model.User;
import com.wavelabs.model.UserAdvertIntrested;
import com.wavelabs.test.builder.ObjectBuilder;
import com.wavelabs.test.builder.UserIntrestAdvertBuilder;
import com.wavelabs.utility.Helper;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Helper.class)
public class UserAdvertIntrestDaoTest {

	private Session session;

	@Before
	public void setUp() {

		session = mock(Session.class);
		PowerMockito.mockStatic(Helper.class);
		when(Helper.getSession()).thenReturn(session);

	}

	@Test
	public void testPersistUserAdvertIntrest1() {

		UserAdvertIntrested uai = UserIntrestAdvertBuilder.userAdvertIntrestBuild();
		Transaction tx = mock(Transaction.class);
		when(session.beginTransaction()).thenReturn(tx);
		when(session.save(any(UserAdvertIntrested.class))).thenReturn(111);
		boolean flag = ObjectBuilder.getUserAdvertDao().persistUserAdverIntrested(uai);
		Assert.assertEquals(true, flag);

	}

	@Test
	public void testPersistUserAdvertIntrest2() {

		UserAdvertIntrested uai = UserIntrestAdvertBuilder.userAdvertIntrestBuild();
		when(session.save(any(UserAdvertIntrested.class))).thenReturn(111);
		boolean flag = ObjectBuilder.getUserAdvertDao().persistUserAdverIntrested(uai);
		Assert.assertEquals(false, flag);
	}

	@Test
	public void testGet1() {

		UserAdvertIntrested uai = UserIntrestAdvertBuilder.userAdvertIntrestBuild();
		when(session.get(eq(UserAdvertIntrested.class), anyInt())).thenReturn(uai);
		UserAdvertIntrested newUai = ObjectBuilder.getUserAdvertDao().get(2);
		Gson gson = new Gson();
		Assert.assertEquals(gson.toJson(uai), gson.toJson(newUai));
	}

	@Test
	public void testGet2() {

		Session session = null;
		when(Helper.getSession()).thenReturn(session);
		UserAdvertIntrested newUai = ObjectBuilder.getUserAdvertDao().get(2);
		Assert.assertEquals(null, newUai);
	}

	@Test
	public void testGetUserAdvertIntrested() {

		List<UserAdvertIntrested> list = new ArrayList<>();
		list.add(mock(UserAdvertIntrested.class));
		list.add(mock(UserAdvertIntrested.class));
		User user = mock(User.class);
		when(session.get(eq(User.class), anyInt())).thenReturn(user);
		Query query = mock(Query.class);
		when(session.createQuery(anyString())).thenReturn(query);
		List<UserAdvertIntrested> newList = ObjectBuilder.getUserAdvertDao().getUserAdvertIntrested(2);
		Assert.assertEquals(0, newList.size());
	}
}
