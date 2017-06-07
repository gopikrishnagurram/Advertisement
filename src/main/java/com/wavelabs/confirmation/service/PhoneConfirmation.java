package com.wavelabs.confirmation.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.wavelabs.model.Advertisement;
import com.wavelabs.model.User;

public class PhoneConfirmation {

	public static final String ACCOUNT_SID = "AC5b38f60d5a68bd5e31d5a305c498a162";
	public static final String AUTH_TOKEN = "38202f6199ca607aa67aea58de0260b0";
	public static final String TWILIO_NUMBER = "+15165709515";
	private static final Logger LOGGER = Logger.getLogger(PhoneConfirmation.class);

	private PhoneConfirmation() {

	}

	public static void sendPhoneConfirmation(Advertisement add, User user, String type) {

		String message = "";
		if ("POST".equals(type)) {
			message = "Hey " + user.getFirstName() + ",\n";
			message = message + " Your advertisement successfully posted. \n";
			message = message + createAdvertisementMessage(add);
		} else if ("APPLY".equals(type)) {
			message = "Hey " + add.getUser().getFirstName() + ", \n";
			message = message + " Your advertiment got response from :" + user.getFirstName() + "\n";
			message = message + "Contact details : \n";
			message = message + " Email : " + user.getEmail() + " \n Phone : " + user.getPhone();
		}
		sendSMS(message, add.getUser().getPhone());
	}

	private static void sendSMS(String confirmationMessage, String phoneNumber) {
		try {
			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("Body", confirmationMessage));
			params.add(new BasicNameValuePair("To", "+91" + phoneNumber));
			params.add(new BasicNameValuePair("From", TWILIO_NUMBER));
			MessageFactory messageFactory = client.getAccount().getMessageFactory();
			messageFactory.create(params);
		} catch (TwilioRestException e) {
			LOGGER.error(e);
		}
	}

	private static String createAdvertisementMessage(Advertisement add) {

		return "name : " + add.getName() + "\n" + "description : " + add.getDescription() + "\n Location :"
				+ add.getLocation();
	}
}
