package com.wavelabs.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wavelabs.model.Advertisement;
import com.wavelabs.model.Message;
import com.wavelabs.service.AdvertisementService;
import com.wavelabs.solr.service.SolrSearchService;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Component
public class AdvertisementResource {

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private SolrSearchService solrService;

	public AdvertisementResource(AdvertisementService service, SolrSearchService solrService) {

		advertisementService = service;
		this.solrService = solrService;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/Advertisement/{id}")
	@ApiResponses({ @ApiResponse(code = 200, message = "Advertisement successfully returned"),
			@ApiResponse(code = 404, message = "Advertisement not found") })
	public ResponseEntity getAdvertisement(@ApiParam(name = "id", required = true) @PathVariable("id") int id) {

		Advertisement add = advertisementService.getAdvertisement(id);

		if (add == null) {
			Message message = new Message();
			message.setId(500);
			message.setText("Advertisement not found");
			return ResponseEntity.status(500).body(message);
		}
		return ResponseEntity.status(200).body(add);
	}

	
	@RequestMapping(value = "/Advertisement", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveAdvertisement(@RequestBody Advertisement add) {

		boolean flag = advertisementService.persistAdvertisement(add);
		Message message = new Message();
		if (flag) {
			message.setId(200);
			message.setText("Advertisement is saved successfully");
			return ResponseEntity.status(HttpStatus.CREATED).body(message);
		} else {
			message.setId(500);
			message.setText("Failed please try again later");
			return ResponseEntity.status(500).body(message);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/Advertisement/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity updateAdvertisement(@PathVariable("id") Integer id, @RequestBody Advertisement add) {
		add.setId(id);
		Advertisement newAdd = advertisementService.updateAdvertisement(add);
		if (newAdd != null) {
			return ResponseEntity.status(200).body(newAdd);

		} else {
			Message message = new Message();
			message.setId(500);
			message.setText("Server failed to update given object");
			return ResponseEntity.status(500).body(message);
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/Advertisement/{id}", method = RequestMethod.DELETE)
	public ResponseEntity deleteAdvertisement(@PathVariable("id") int id) {

		boolean flag = advertisementService.deleteAdvertisement(id);
		Message message = new Message();
		if (flag) {
			message.setId(200);
			message.setText("Advertisement deleted successfully");
			return ResponseEntity.status(200).body(message);
		} else {
			message.setId(500);
			message.setText("Server error in deletion process");
			return ResponseEntity.status(500).body(message);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/Advertisement/search/{search}")
	public ResponseEntity searchAdd(@PathVariable("search") String search) {
		Advertisement adds[] = solrService.getAdverts(search);
		if (adds.length != 0) {
			return ResponseEntity.status(200).body(adds);
		} else {
			Message messge = new Message();
			messge.setId(404);
			messge.setText("Didn't find the matched search result");
			return ResponseEntity.status(404).body(adds);
		}
	}

}
