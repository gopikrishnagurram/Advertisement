package com.wavelabs.service.test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gson.Gson;
import com.wavelabs.dao.UserAdvertIntrestedDao;
import com.wavelabs.model.UserAdvertIntrested;
import com.wavelabs.service.UserAdvertIntrestService;
import com.wavelabs.test.builder.UserIntrestAdvertBuilder;

@RunWith(MockitoJUnitRunner.class)
public class UserAdvertIntrestServiceTest {

	@Mock
	private UserAdvertIntrestedDao udao;

	@InjectMocks
	private UserAdvertIntrestService service;

	@Test
	public void testPersistUserAdvertIntrest() {

		UserAdvertIntrested uai = UserIntrestAdvertBuilder.userAdvertIntrestBuild();
		when(udao.persistUserAdverIntrested(any(UserAdvertIntrested.class))).thenReturn(true);
		boolean flag = service.persistUserAdvert(uai);
		Assert.assertEquals(true, flag);
	}

	@Test
	public void testGetUserAdvertIntrest() {

		UserAdvertIntrested uai = UserIntrestAdvertBuilder.userAdvertIntrestBuild();
		List<UserAdvertIntrested> list = new ArrayList<>();
		list.add(uai);
		when(udao.getUserAdvertIntrested(anyInt())).thenReturn(list);
		List<UserAdvertIntrested> newList = service.getAllUserAdvertIntrests(2);
		Assert.assertEquals(list.size(), newList.size());
	}

	@Test
	public void testDeleteAdvertIntrest() {
		UserAdvertIntrested uai = UserIntrestAdvertBuilder.userAdvertIntrestBuild();
		when(udao.get(anyInt())).thenReturn(uai);
		Gson gson = new Gson();
		UserAdvertIntrested newUai = service.getUserAdvert(2);
		Assert.assertEquals(gson.toJson(uai), gson.toJson(newUai));
	}
}
