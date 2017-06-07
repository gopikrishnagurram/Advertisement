package com.wavelabs;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.wavelabs.model.TokenInfo;
import com.wavelabs.utils.Constants;
import com.wavelabs.utils.TimedLRUCache;

/**
 * This class is to manage the AuthenticationToken cache. This class would keep
 * AuthTokens and the Usernames in Cache and would reduce the api calls to the
 * NBOS.IO
 * 
 * @author bikshapathi
 *
 */
public class AuthenticationTokenCache {

	private static Logger logger = LoggerFactory.getLogger(AuthenticationTokenCache.class);
	private static final int MAX_SIZE = 3000;
	private static final long TTL = 1 * 60 * 1000; // 30 minutes
	private TokenInfo tokenInfo;
	private static AuthenticationTokenCache instance = new AuthenticationTokenCache(MAX_SIZE, TTL);

	/**
	 * Method to get the singleton instance object
	 * 
	 * @return
	 */
	public AuthenticationTokenCache() {

	}

	public static AuthenticationTokenCache getInstance() {
		return instance;
	}

	private TimedLRUCache<String, TokenInfo> authTokenCache = null;

	/**
	 * Private constructor for AuthenticationTokenCache.
	 * 
	 * @param maxEntries
	 *            for maximum number of Entries in the cache
	 * @param timeToLive
	 *            for the entried to be in the cache
	 */
	private AuthenticationTokenCache(int maxEntries, long timeToLive) {
		authTokenCache = new TimedLRUCache<>(maxEntries, timeToLive);
	}

	/**
	 * Method to validate AuthToken, Post a Request to nbos.in with authToken,
	 * get it validated if valid put the authToken in Cache.
	 * 
	 * @param accessToken
	 *            would be user provided
	 * @param authorization
	 * @param moduleKey
	 * @param url
	 *            wavelabs nbos.in url
	 * @return
	 */
	public boolean validateAuthToken(ServletRequest request, String accessToken, String authorization, String moduleKey,
			String url) {
		TokenInfo tokenInfoFromCache = authTokenCache.get(accessToken);
		if (tokenInfoFromCache == null) {
			HttpHeaders headers = new HttpHeaders();
			// Setting the authorization and module key is headers to call
			headers.set(Constants.AUTHORIZATION_LABEL, authorization);
			headers.set(Constants.MODULE_KEY_LABEL, moduleKey);
			String uri = url + accessToken;
			Map<String, String> vars = new HashMap<>();
			vars.put(Constants.ACCESS_TOKEN, accessToken);
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
			logger.info("..Calling Wavelabs Authentication Api..");
			ResponseEntity<TokenInfo> object = restTemplate.exchange(uri, HttpMethod.GET,
					new HttpEntity<Object>(headers), TokenInfo.class, vars);
			logger.info("..Response Status from Wavelabs Authentication Api.." + object.getStatusCode());
			if (object != null && object.getStatusCode() == HttpStatus.OK && object.getBody() != null) {
				tokenInfo = object.getBody();
				authTokenCache.put(accessToken, tokenInfo);
				request.setAttribute("tokenInfo", tokenInfo);
			}
		} else {
			request.setAttribute("tokenInfo", tokenInfoFromCache);
		}
		if (tokenInfo == null && tokenInfoFromCache == null) {
			return false;
		}
		return true;
	}

	/**
	 * Method to remove authToken from Cache
	 * 
	 * @param authenticationToken
	 */
	public void remove(String authToken) {
		if (authToken != null) {
			authTokenCache.remove(authToken);
		}
	}
}