package com.wavelabs.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wavelabs.job.service.SolrJobIndexScheduleService;
import com.wavelabs.model.Message;

@RestController
public class SolrJob {

	@RequestMapping("dataflush/off")
	public ResponseEntity<Message> stopSolrJob() {

		boolean flag = SolrJobIndexScheduleService.stopSchedule();
		Message message = new Message();
		if (flag) {
			message.setId(200);
			message.setText("Solr auto index stopped Successfully");
			return ResponseEntity.status(200).body(message);
		} else {
			message.setId(500);
			message.setText("Problem occured");
			return ResponseEntity.status(500).body(message);
		}
	}

	@RequestMapping("dataflush/on/{time}")
	public ResponseEntity<Message> startSolrJob(@PathVariable("time") Integer time) {

		boolean flag = SolrJobIndexScheduleService.startSchedule(time);
		Message message = new Message();
		if (flag) {
			message.setId(200);
			message.setText("Solr auto index completed Successfully");
			return ResponseEntity.status(200).body(message);
		} else {
			message.setId(500);
			message.setText("Problem occured");
			return ResponseEntity.status(500).body(message);
		}
	}

}
