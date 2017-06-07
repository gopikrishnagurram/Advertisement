package com.wavelabs.dao.test;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.Gson;
import com.wavelabs.model.User;
import com.wavelabs.test.builder.ObjectBuilder;
import com.wavelabs.utility.Helper;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Helper.class)
public class UserDaoTest {

	@Test
	public void testGetUser() {

		User user = new User(1, "gopi", "g@gmail.com", "90321188664");
		PowerMockito.mockStatic(Helper.class);
		Session session = mock(Session.class);
		when(Helper.getSession()).thenReturn(session);
		when(session.get(eq(User.class), anyInt())).thenReturn(user);
		User newUser = ObjectBuilder.getUserDAO().getUser(2);
		Gson gson = new Gson();
		Assert.assertEquals(gson.toJson(user), gson.toJson(newUser));
	}

}
