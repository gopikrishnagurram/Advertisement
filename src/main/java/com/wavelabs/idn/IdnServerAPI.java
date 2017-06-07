package com.wavelabs.idn;

import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.wavelabs.model.Member;
import com.wavelabs.model.User;
import com.wavelabs.utils.Constants;
/**
 * Gives methods to call the Idn server endpoints.
 * @author gopikrishnag
 *
 */
public class IdnServerAPI {

	private static final String getUserEndPoint = "http://api.qa1.nbos.in/api/identity/v0/users/";
	
	/**
	 * Gives the User object from Idn Server
	 * @param accessToekn
	 * @param member
	 * @return
	 */
	public User getUser(String accessToekn, Member member) {
		HttpHeaders httpHeader = setAccessTokenHeader(accessToekn);
		RestTemplate restTemplate = getRestTemplate();
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		HashMap<String, String> vars = getMapForUriVariables(Constants.MEMBER, member.getUuid());
		ResponseEntity<User> exchange = restTemplate.exchange(getUserEndPoint+member.getUuid(), HttpMethod.GET,
				new HttpEntity<Object>(httpHeader), User.class, vars);
		ResponseEntity<User> object = exchange;
		User user = object.getBody();
		return user;
	}

	private HttpHeaders setAccessTokenHeader(String accessToekn) {
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.set("Authorization", Constants.BEARER + accessToekn);
		return httpHeader;
	}

	private RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	private HashMap<String, String> getMapForUriVariables(String key, String value) {
		HashMap<String, String> vars = new HashMap<String, String>();
		vars.put(key, value);
		return vars;
	}
}
