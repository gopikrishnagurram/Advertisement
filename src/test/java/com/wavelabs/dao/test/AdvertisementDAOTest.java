package com.wavelabs.dao.test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.Gson;
import com.wavelabs.model.Advertisement;
import com.wavelabs.test.builder.AdvertisementBuilder;
import com.wavelabs.test.builder.ObjectBuilder;
import com.wavelabs.utility.Helper;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Helper.class)
public class AdvertisementDAOTest {

	private Session session;

	@Before
	public void setUp() {

		session = mock(Session.class);
		PowerMockito.mockStatic(Helper.class);
		when(Helper.getSession()).thenReturn(session);
	}

	@Test
	public void testGetAdvertisement() {
		Advertisement oldAdd = AdvertisementBuilder.advertisementBuild();
		when(session.get(eq(Advertisement.class), any())).thenReturn(oldAdd);
		Advertisement newAdd = ObjectBuilder.getAdvertismentDao().getAdvertisement(2);
		Gson gson = new Gson();

		Assert.assertEquals(gson.toJson(oldAdd), gson.toJson(newAdd));
	}

	@Test
	public void testPersistAdvertisement2() {
		Advertisement oldAdd = AdvertisementBuilder.advertisementBuild();
		when(session.save(any(Advertisement.class))).thenReturn(111);
		boolean flag = ObjectBuilder.getAdvertismentDao().persistAdvertisement(oldAdd);
		Assert.assertEquals(false, flag);
	}

	@Test
	public void testPersistAdvertisement1() {
		Advertisement oldAdd = AdvertisementBuilder.advertisementBuild();
		when(session.save(any(Advertisement.class))).thenReturn(111);
		when(session.beginTransaction()).thenReturn(mock(Transaction.class));
		boolean flag = ObjectBuilder.getAdvertismentDao().persistAdvertisement(oldAdd);
		Assert.assertEquals(true, flag);
	}

	@Test
	public void testUpdateAdvertisement1() {

		Advertisement oldAdd = AdvertisementBuilder.advertisementBuild();
		Transaction tx = mock(Transaction.class);
		when(session.beginTransaction()).thenReturn(tx);
		doNothing().when(session).update(oldAdd);
		doNothing().when(tx).commit();
		Advertisement newAdd = ObjectBuilder.getAdvertismentDao().updateAdvertisement(oldAdd);
		verify(tx, times(1)).commit();
		Gson gson = new Gson();
		Assert.assertEquals(oldAdd.getDescription(), newAdd.getDescription());
		Assert.assertEquals(oldAdd.getLocation(), oldAdd.getLocation());
		Assert.assertEquals(oldAdd.getName(), newAdd.getName());
		Assert.assertEquals(oldAdd.getType(), newAdd.getType());
		Assert.assertEquals(oldAdd.getId(), oldAdd.getId());
		Assert.assertEquals(gson.toJson(oldAdd), gson.toJson(newAdd));
		Assert.assertEquals(oldAdd.getUser().getEmail(), newAdd.getUser().getEmail());
		Assert.assertEquals(oldAdd.getUser().getPhone(), newAdd.getUser().getPhone());
		Assert.assertEquals(oldAdd.getUser().getId(), newAdd.getUser().getId());
	}

	@Test
	public void testUpdateAdvertisement2() {

		Advertisement oldAdd = AdvertisementBuilder.advertisementBuild();
		Transaction tx = mock(Transaction.class);
		when(session.beginTransaction()).thenReturn(tx);
		doNothing().when(session).update(oldAdd);
		PowerMockito.doThrow(new TransactionException("fake exception to check the code")).when(tx).commit();
		Advertisement newAdd = ObjectBuilder.getAdvertismentDao().updateAdvertisement(oldAdd);
		verify(tx, timeout(0)).commit();
		Assert.assertEquals(null, newAdd);
	}

	@Test
	public void testDeleteAdvertisement1() {
		Transaction tx = mock(Transaction.class);
		when(session.beginTransaction()).thenReturn(tx);
		doNothing().when(session).delete(2);
		Advertisement oldAdd = AdvertisementBuilder.advertisementBuild();
		when(session.get(eq(Advertisement.class), any())).thenReturn(oldAdd);
		boolean flag = ObjectBuilder.getAdvertismentDao().deleteAdvertisement(2);
		Assert.assertEquals(true, flag);
	}

	@Test
	public void testDeleteAdvertisement2() {
		Transaction tx = mock(Transaction.class);
		when(session.beginTransaction()).thenReturn(tx);
		doNothing().when(session).delete(2);
		Advertisement oldAdd = AdvertisementBuilder.advertisementBuild();
		when(session.get(eq(Advertisement.class), any())).thenReturn(oldAdd);
		PowerMockito.doThrow(new TransactionException("fake exception to check the code")).when(tx).commit();
		boolean flag = ObjectBuilder.getAdvertismentDao().deleteAdvertisement(2);
		Assert.assertEquals(false, flag);
	}

	@Test
	public void testGetAllAdvertisementsOfUser1() {

		List<Advertisement> listOfAdvertisements = new ArrayList<>();
		listOfAdvertisements.add(AdvertisementBuilder.advertisementBuild());
		listOfAdvertisements.add(AdvertisementBuilder.advertisementBuild());
		Query query = mock(Query.class);
		when(session.createQuery(anyString())).thenReturn(query);
		when(query.list()).thenReturn(listOfAdvertisements);
		List<Advertisement> newAdds = ObjectBuilder.getAdvertismentDao().getAllAdvertisementsOfUser(2);
		Assert.assertEquals(listOfAdvertisements.size(), newAdds.size());
	}

	@Test
	public void testGetAllAdvertisementsOfUser2() {

		List<Advertisement> listOfAdvertisements = new ArrayList<>();
		listOfAdvertisements.add(AdvertisementBuilder.advertisementBuild());
		listOfAdvertisements.add(AdvertisementBuilder.advertisementBuild());
		Query query = mock(Query.class);
		PowerMockito.doThrow(new QueryException("Fake exception for test")).when(session).createQuery(anyString());
		when(query.list()).thenReturn(listOfAdvertisements);
		List<Advertisement> newAdds = ObjectBuilder.getAdvertismentDao().getAllAdvertisementsOfUser(2);
		verify(query, times(0)).list();
		Assert.assertEquals(0, newAdds.size());
	}

	@After
	public void tearDown() {
		verify(session, times(1)).close();
	}
}
