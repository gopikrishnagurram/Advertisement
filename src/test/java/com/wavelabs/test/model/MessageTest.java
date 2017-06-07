package com.wavelabs.test.model;

import org.junit.Assert;
import org.junit.Test;

import com.wavelabs.model.Message;

public class MessageTest {

	@Test
	public void testMessage() {

		Message message = new Message();
		message.setId(200);
		message.setText("Hey");
		Assert.assertEquals(200, message.getId());
		Assert.assertEquals("Hey", message.getText());
	}
}
