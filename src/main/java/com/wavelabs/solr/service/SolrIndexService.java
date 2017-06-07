package com.wavelabs.solr.service;

import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.wavelabs.model.Advertisement;

public class SolrIndexService implements Job {

	private final static Logger LOGGER = Logger.getLogger(SolrIndexService.class);
	private static SolrClient client;
	private static SessionFactory factory;

	private static void createSolrInstance() {
		client = new HttpSolrClient("http://192.168.99.100:8983/solr/advert");
		LOGGER.info("client connection opend");
	}

	public static Boolean doIndex() {
		Criteria cr = getJobCritiera();
		return listOfJobs(cr);
	}

	@SuppressWarnings("deprecation")
	private static Criteria getJobCritiera() {
		Configuration cfg = new Configuration();
		factory = cfg.configure().buildSessionFactory();
		Session session = factory.openSession();
		return session.createCriteria(Advertisement.class);
	}

	@SuppressWarnings("unchecked")
	private static boolean listOfJobs(Criteria cr) {
		LOGGER.setLevel(Level.INFO);
		return addDocumentsToSolr(cr.list());
	}

	public static boolean addDocumentsToSolr(List<Advertisement> listOfAdvertisements) {
		int i = 0;
		createSolrInstance();

		LOGGER.debug("adding documents into solr is started");
		try {
			for (Advertisement advet : listOfAdvertisements) {
				SolrInputDocument docuemnt = new SolrInputDocument();
				docuemnt.addField("id", advet.getId());
				docuemnt.addField("name", advet.getName());
				docuemnt.addField("type",
						advet.getType().toString().replace("com.wavelabs.modal.enums.AdvertisementType:", ""));
				docuemnt.addField("description", advet.getDescription());
				docuemnt.addField("location", advet.getLocation());
				docuemnt.addField("user_id", advet.getUser().getId());
				client.add(docuemnt);
				LOGGER.info((++i) + "Document added, not commited");
			}
			client.commit();
			LOGGER.info("All documents commited");
			client.close();
			LOGGER.info("Indexing completed and connection closed");
			factory.close();
			return true;
		} catch (Exception e) {
			LOGGER.error(e);
			return false;
		}
	}

	public static void main(String[] args) {
		doIndex();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		doIndex();
	}
}
