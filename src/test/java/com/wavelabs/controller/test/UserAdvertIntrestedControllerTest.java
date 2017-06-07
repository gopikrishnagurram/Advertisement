package com.wavelabs.controller.test;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.wavelabs.model.UserAdvertIntrested;
import com.wavelabs.resource.UserAdvertIntrestedResource;
import com.wavelabs.service.UserAdvertIntrestService;
import com.wavelabs.test.builder.UserIntrestAdvertBuilder;

@RunWith(MockitoJUnitRunner.class)
public class UserAdvertIntrestedControllerTest {

	@Mock
	private UserAdvertIntrestService uais;

	@InjectMocks
	private UserAdvertIntrestedResource resource;

	@Test
	public void testGetUserAdvert() {

		UserAdvertIntrested uai = UserIntrestAdvertBuilder.userAdvertIntrestBuild();
		when(uais.getUserAdvert(anyInt())).thenReturn(uai);
		ResponseEntity entity = resource.getUserAdvertIntrest(1);
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testGetUserAdvert2() {

		UserAdvertIntrested uai = UserIntrestAdvertBuilder.userAdvertIntrestBuild();
		when(uais.getUserAdvert(anyInt())).thenReturn(null);
		ResponseEntity entity = resource.getUserAdvertIntrest(1);
		Assert.assertEquals(404, entity.getStatusCodeValue());
	}

	@Test
	public void testPersisteUserAdvert1() {

		UserAdvertIntrested uai = UserIntrestAdvertBuilder.userAdvertIntrestBuild();
		when(uais.persistUserAdvert(uai)).thenReturn(true);
		ResponseEntity entity = resource.postUserAdvertIntrest(uai);
		Assert.assertEquals(200, entity.getStatusCodeValue());

	}

	@Test
	public void testPersisteUserAdvert2() {

		UserAdvertIntrested uai = UserIntrestAdvertBuilder.userAdvertIntrestBuild();
		when(uais.persistUserAdvert(uai)).thenReturn(false);
		ResponseEntity entity = resource.postUserAdvertIntrest(uai);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@Test
	public void testGetAllUserIntrests() {
		List<UserAdvertIntrested> list = new ArrayList<>();
		list.add(UserIntrestAdvertBuilder.userAdvertIntrestBuild());
		list.add(UserIntrestAdvertBuilder.userAdvertIntrestBuild());
		when(uais.getAllUserAdvertIntrests(2)).thenReturn(list);
		ResponseEntity entity = resource.getUserIntrests(2);
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testGetAllUserIntrests2() {
		List<UserAdvertIntrested> list = new ArrayList<>();
		list.add(UserIntrestAdvertBuilder.userAdvertIntrestBuild());
		list.add(UserIntrestAdvertBuilder.userAdvertIntrestBuild());
		when(uais.getAllUserAdvertIntrests(2)).thenReturn(Collections.emptyList());
		ResponseEntity entity = resource.getUserIntrests(2);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}
}
