package com.wavelabs.test.model;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import com.wavelabs.model.Advertisement;
import com.wavelabs.model.Comment;
import com.wavelabs.model.User;
import com.wavelabs.test.builder.AdvertisementBuilder;

public class CommentTest {

	@Test
	public void testText() {

		Comment comment = new Comment();
		comment.setText("Hey");
		Assert.assertEquals("Hey", comment.getText());
	}

	@Test
	public void testTimeStamp() {

		Comment comment = new Comment();
		Calendar cal = Calendar.getInstance();
		comment.setTimeStamp(cal);
		Assert.assertEquals(cal.toString(), comment.getTimeStamp().toString());
	}

/*	@Test
	public void testUser() {

		Comment comment = new Comment();
		comment.setUser(new User(1, "gopi", "g@gmail.com", "9032118864"));
		Assert.assertEquals("gopi", comment.getUser().getName());
	}*/

	@Test
	public void testAdvertisement() {

		Comment comment = new Comment();
		Advertisement add = AdvertisementBuilder.advertisementBuild();
		comment.setAdvertisement(add);
		comment.setId(1);
		Assert.assertEquals(add.getLocation(), comment.getAdvertisement().getLocation());
		Assert.assertEquals(new Integer(1), comment.getId());
	}
}
