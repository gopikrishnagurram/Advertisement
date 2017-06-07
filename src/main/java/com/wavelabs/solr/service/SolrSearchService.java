package com.wavelabs.solr.service;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wavelabs.dao.UserDAO;
import com.wavelabs.model.Advertisement;
import com.wavelabs.model.User;
import com.wavelabs.model.enums.AdvertisementType;

@Service
public class SolrSearchService {
	private static SolrClient client = null;
	@SuppressWarnings("squid:S1313")
	private static final String ip = "http://192.168.99.100:8983/solr/advert";
	private static final Logger LOGGER = Logger.getLogger(SolrSearchService.class);
	@Autowired
	private UserDAO userDao;

	private SolrSearchService() {

	}

	private static String parseString(String str) {
		return str.replace("[", "").replace("]", "");
	}

	@SuppressWarnings("deprecation")
	private static void intillization() {
		client = new HttpSolrClient(ip);
	}

	public Advertisement[] getAdverts(String searchTerm) {
		intillization();
		Advertisement[] add = {};
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("spellcheck", "on");
		params.set("q", searchTerm + "~3");
		try {
			QueryResponse response = client.query(params);
			SolrDocumentList documentList = response.getResults();
			add = new Advertisement[documentList.size()];
			int i = 0;
			for (SolrDocument document : documentList) {
				Integer id = Integer.parseInt(parseString(document.getFieldValue("id").toString()));
				String name = parseString(document.getFieldValue("name").toString());
				String type = parseString(document.getFieldValue("type").toString());
				String description = parseString(document.getFieldValue("description").toString());
				Integer userId = Integer.parseInt(parseString(document.getFieldValue("user_id").toString()));
				String location = parseString(document.getFieldValue("location").toString());
				User user = userDao.getUser(userId);
				Advertisement solrAdd = createAdvertisement(user, id, name, description, type, location);
				add[i++] = solrAdd;
			}
			return add;
		} catch (Exception e) {
			LOGGER.error(e);
			return add;
		}
	}

	private Advertisement createAdvertisement(User user, Integer id, String name, String description, String type,
			String location) {

		Advertisement add = new Advertisement();
		add.setDescription(description);
		add.setId(id);
		add.setName(name);
		add.setUser(user);
		add.setLocation(location);
		add.setType(AdvertisementType.valueOf(type));
		return add;
	}

	public static void main(String[] args) {

		new SolrSearchService().getAdverts("phone");

	}
}
