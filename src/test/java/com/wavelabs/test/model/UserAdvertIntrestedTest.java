package com.wavelabs.test.model;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.wavelabs.model.Advertisement;
import com.wavelabs.model.User;
import com.wavelabs.model.UserAdvertIntrested;
import com.wavelabs.test.builder.AdvertisementBuilder;

public class UserAdvertIntrestedTest {

	@Test
	public void testUserAdvert() {
		User user = new User(1, "a", "b", "c");
		Advertisement add = AdvertisementBuilder.advertisementBuild();
		UserAdvertIntrested uai = new UserAdvertIntrested();
		uai.setUser(user);
		uai.setAdvert(add);
		uai.setId(1);
		Gson gson = new Gson();
		Assert.assertEquals(gson.toJson(user), gson.toJson(uai.getUser()));
		Assert.assertEquals(gson.toJson(add), gson.toJson(uai.getAdvert()));
		Assert.assertEquals(1, uai.getId());
	}
}
