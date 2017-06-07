package com.wavelabs.service.test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
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
import com.wavelabs.dao.AdvertisementDao;
import com.wavelabs.model.Advertisement;
import com.wavelabs.service.AdvertisementService;
import com.wavelabs.test.builder.AdvertisementBuilder;

@RunWith(MockitoJUnitRunner.class)
public class AdvertisementServiceTest {

	@Mock
	private AdvertisementDao addDao;

	@InjectMocks
	private AdvertisementService service;

	@Test
	public void testPersistAdvertisement() throws Exception {

		Advertisement add = mock(Advertisement.class);
		/*
		 * ConfirmationThread ct = mock(ConfirmationThread.class);
		 * PowerMockito.whenNew(ConfirmationThread.class).withAnyArguments().
		 * thenReturn(ct); Thread thread = mock(Thread.class);
		 * PowerMockito.whenNew(Thread.class).withArguments(ConfirmationThread.
		 * class).thenReturn(thread);
		 */
		when(addDao.persistAdvertisement(add)).thenReturn(true);
		boolean flag = service.persistAdvertisement(add);
		Assert.assertEquals(true, flag);
	}

	@Test
	public void testPersistAdvertisement2() throws Exception {

		Advertisement add = mock(Advertisement.class);
		/*
		 * ConfirmationThread ct = mock(ConfirmationThread.class);
		 * PowerMockito.whenNew(ConfirmationThread.class).withAnyArguments().
		 * thenReturn(ct); Thread thread = mock(Thread.class);
		 * PowerMockito.whenNew(Thread.class).withArguments(ConfirmationThread.
		 * class).thenReturn(thread);
		 */
		when(addDao.persistAdvertisement(add)).thenReturn(false);
		boolean flag = service.persistAdvertisement(add);
		Assert.assertEquals(false, flag);
	}

	@Test
	public void testGetAdvertisement1() {

		Advertisement add = AdvertisementBuilder.advertisementBuild();
		when(addDao.getAdvertisement(anyInt())).thenReturn(add);
		Advertisement newAdd = service.getAdvertisement(2);
		Gson gson = new Gson();
		Assert.assertEquals(gson.toJson(add), gson.toJson(newAdd));

	}

	@Test
	public void testDeleteAdvertisement() {

		when(addDao.deleteAdvertisement(anyInt())).thenReturn(true);
		boolean flag = service.deleteAdvertisement(2);
		Assert.assertEquals(true, flag);
	}

	@Test
	public void testUpdateAdvertisement() {
		Advertisement add = AdvertisementBuilder.advertisementBuild();
		when(addDao.updateAdvertisement(any(Advertisement.class))).thenReturn(add);
		Advertisement newAdd = service.updateAdvertisement(add);
		Gson gson = new Gson();
		Assert.assertEquals(gson.toJson(add), gson.toJson(newAdd));
	}

	@Test
	public void testGetListOfAdvertisements() {

		List<Advertisement> adds = new ArrayList<>();
		adds.add(AdvertisementBuilder.advertisementBuild());
		adds.add(AdvertisementBuilder.advertisementBuild());
		when(addDao.getAllAdvertisementsOfUser(anyInt())).thenReturn(adds);
		List<Advertisement> listOfAdds = service.getAllAdvertisementOfUser(2);
		Assert.assertEquals(adds.size(), listOfAdds.size());

	}

}
