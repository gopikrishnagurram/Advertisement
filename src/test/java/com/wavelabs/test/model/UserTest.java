package com.wavelabs.test.model;

import org.junit.Assert;
import org.junit.Test;

import com.wavelabs.model.User;

public class UserTest {

	@Test
	public void testUser() {

		User user = new User();
		user.setId(2);
		user.setFirstName("nutana");
		user.setEmail("n@gmail.com");
		user.setPhone("9032");
		Assert.assertEquals(2, user.getId());
		Assert.assertEquals("nutana", user.getFirstName());
		Assert.assertEquals("n@gmail.com", user.getEmail());
		Assert.assertEquals("9032", user.getPhone());
	}

}
