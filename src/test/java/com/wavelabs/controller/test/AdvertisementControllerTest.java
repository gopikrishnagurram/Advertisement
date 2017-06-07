package com.wavelabs.controller.test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.wavelabs.model.Advertisement;
import com.wavelabs.model.Message;
import com.wavelabs.resource.AdvertisementResource;
import com.wavelabs.service.AdvertisementService;
import com.wavelabs.solr.service.SolrSearchService;
import com.wavelabs.test.builder.AdvertisementBuilder;
@RunWith(MockitoJUnitRunner.class)
public class AdvertisementControllerTest {

	@Mock
	private SolrSearchService solrService;

	@Mock
	private AdvertisementService addService;
	

	@InjectMocks
	private AdvertisementResource resource;
	
	@Test
	public void testGetAdvertisement() throws Exception {

		Advertisement add = AdvertisementBuilder.advertisementBuild();
		when(addService.getAdvertisement(anyInt())).thenReturn(add);
		ResponseEntity entity = resource.getAdvertisement(2);
		Assert.assertEquals(200, entity.getStatusCodeValue());

	}

	@Test
	public void testGetAdvertisement2() throws Exception {
		Advertisement add = AdvertisementBuilder.advertisementBuild();
		when(addService.getAdvertisement(anyInt())).thenReturn(null);
		ResponseEntity entity = resource.getAdvertisement(1);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@Test
	public void testSaveAdvertisement() throws Exception {

		Advertisement add = AdvertisementBuilder.advertisementBuild();
		when(addService.persistAdvertisement(any(Advertisement.class))).thenReturn(true);
		ResponseEntity<Message> entity = resource.saveAdvertisement(add);
		Assert.assertEquals(201, entity.getStatusCodeValue());
	}

	@Test
	public void testSaveAdvertisement2() throws Exception {

		Advertisement add = AdvertisementBuilder.advertisementBuild();
		when(addService.persistAdvertisement(any(Advertisement.class))).thenReturn(false);
		ResponseEntity<Message> entity = resource.saveAdvertisement(add);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@Test
	public void testUpdateAdvertisement() throws Exception {

		Advertisement add = AdvertisementBuilder.advertisementBuild();
		when(addService.updateAdvertisement(any(Advertisement.class))).thenReturn(add);
		ResponseEntity entity = resource.updateAdvertisement(add.getId(), add);
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testUpdateAdvertisement2() throws Exception {

		Advertisement add = AdvertisementBuilder.advertisementBuild();
		when(addService.updateAdvertisement(any(Advertisement.class))).thenReturn(null);
		ResponseEntity entity = resource.updateAdvertisement(add.getId(), add);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@Test
	public void testDeleteAdvertisement() throws Exception {

		when(addService.deleteAdvertisement(anyInt())).thenReturn(true);
		ResponseEntity entity = resource.deleteAdvertisement(1);
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testDeleteAdvertisement2() throws Exception {

		when(addService.deleteAdvertisement(anyInt())).thenReturn(false);
		ResponseEntity entity = resource.deleteAdvertisement(1);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@Test
	public void testSearchAdd() throws Exception {

		Advertisement add[] = { AdvertisementBuilder.advertisementBuild(), AdvertisementBuilder.advertisementBuild() };
		when(solrService.getAdverts(anyString())).thenReturn(add);
		ResponseEntity entity = resource.searchAdd("some");
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}
	@Test
	public void testSearchAdd2() throws Exception {

		Advertisement add[] = {};
		when(solrService.getAdverts(anyString())).thenReturn(add);
		ResponseEntity entity = resource.searchAdd("some");
		Assert.assertEquals(404, entity.getStatusCodeValue());
	}
}
