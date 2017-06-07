package com.wavelabs.controller.test;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.ResponseEntity;

import com.wavelabs.job.service.SolrJobIndexScheduleService;
import com.wavelabs.resource.SolrJob;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SolrJobIndexScheduleService.class)
public class SolrJobControllerTest {

	@InjectMocks
	private SolrJob solrJob;

	@Test
	public void testScheduleOn() {

		PowerMockito.mockStatic(SolrJobIndexScheduleService.class);
		when(SolrJobIndexScheduleService.startSchedule(anyInt())).thenReturn(true);
		ResponseEntity entity = solrJob.startSolrJob(20);
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testScheduleOn2() {

		PowerMockito.mockStatic(SolrJobIndexScheduleService.class);
		when(SolrJobIndexScheduleService.startSchedule(200)).thenReturn(false);
		ResponseEntity entity = solrJob.startSolrJob(20);
		Assert.assertEquals(500, entity.getStatusCodeValue());

	}

	@Test
	public void testScheduleOff1() {

		PowerMockito.mockStatic(SolrJobIndexScheduleService.class);
		when(SolrJobIndexScheduleService.stopSchedule()).thenReturn(true);
		ResponseEntity entity = solrJob.stopSolrJob();
		Assert.assertEquals(200, entity.getStatusCodeValue());

	}

	@Test
	public void testScheduleOff2() {

		PowerMockito.mockStatic(SolrJobIndexScheduleService.class);
		when(SolrJobIndexScheduleService.stopSchedule()).thenReturn(false);
		ResponseEntity entity = solrJob.stopSolrJob();
		Assert.assertEquals(500, entity.getStatusCodeValue());

	}
}
