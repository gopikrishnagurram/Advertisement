package com.wavelabs.test.model;

import org.junit.Assert;
import org.junit.Test;

import com.wavelabs.model.Advertisement;
import com.wavelabs.model.enums.AdvertisementType;

public class AdvertisementTest {

	@Test
	public void testGetName() {

		Advertisement add = new Advertisement();
		add.setName("Gopi krishna");
		Assert.assertEquals("Gopi krishna", add.getName());

	}

	@Test
	public void testGetDescription() {

		Advertisement add = new Advertisement();
		add.setDescription("g@gmail.com");
		Assert.assertEquals("g@gmail.com", add.getDescription());
	}

	@Test
	public void testGetLocation() {

		Advertisement add = new Advertisement();
		add.setLocation("Khammam");
		Assert.assertEquals("Khammam", add.getLocation());
	}

	@Test
	public void testAdvertismentType() {

		Advertisement add = new Advertisement();
		add.setType(AdvertisementType.BUSINESS);
		Assert.assertEquals(AdvertisementType.BUSINESS, add.getType());
	}
}
