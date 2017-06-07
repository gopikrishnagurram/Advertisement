package com.wavelabs.job.service;

import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.wavelabs.solr.service.SolrIndexService;

public class SolrJobIndexScheduleService {

	private static final Logger LOGGER = Logger.getLogger(SolrJobIndexScheduleService.class);

	private SolrJobIndexScheduleService() {

	}

	private static Scheduler schedule = null;

	public static Scheduler getSchedule() {
		return schedule;
	}

	public static void setSchedule(Scheduler schedule) {
		SolrJobIndexScheduleService.schedule = schedule;
	}

	public static Boolean startSchedule(Integer time) {

		try {
			JobDetail jobDetail = JobBuilder.newJob(SolrIndexService.class).withIdentity("solrJob", "insert").build();
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simpleTrigger", "insert")
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(time).repeatForever())
					.build();
			schedule = StdSchedulerFactory.getDefaultScheduler();
			schedule.start();
			schedule.scheduleJob(jobDetail, trigger);
			return true;
		} catch (Exception e) {
			LOGGER.error(e);
			return false;
		}
	}

	public static Boolean stopSchedule() {
		try {
			schedule.shutdown();
			return true;
		} catch (Exception e) {
			LOGGER.error(e);
			return false;
		}
	}
}
