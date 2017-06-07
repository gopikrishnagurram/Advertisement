package com.wavelabs;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.wavelabs.idn.IdnServerAPI;
import com.wavelabs.model.Message;
import com.wavelabs.model.Modules;
import com.wavelabs.model.Tenant;
import com.wavelabs.model.TokenInfo;
import com.wavelabs.model.User;
import com.wavelabs.persist.Persister;
import com.wavelabs.record.existence.ObjectChecker;
import com.wavelabs.utils.Constants;

/**
 * This the Filter class to filter all the User requests for Auth tokens. This
 * would validate if the requested URL required user login. If it requires user
 * login, this class would authenticate the request with NBOS.in
 * 
 * @author bikshapathi
 *
 */
@Component
@Order(1)
public class NbosFilter implements Filter {
	@Autowired
	private Environment env;

	@Autowired
	private ObjectChecker objectChecker;

	@Autowired
	private Persister persister;

	private static final String moduleToken = "MOD:11296761-d4d8-4975-b27f-41090a607702";

	@Autowired
	private IdnServerAPI idnServer;

/*	@Autowired
	private AuthenticationTokenCache cache;*/

	private static Logger logger = LoggerFactory.getLogger(NbosFilter.class);

	private AuthenticationTokenCache authTokenCache = AuthenticationTokenCache.getInstance();

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	/**
	 * Method to filter all the user requests and authenticate them
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		if (needLogin(httpServletRequest)) {
			String accessToken = httpServletRequest.getHeader(Constants.ACCESS_TOKEN);
			String url = env.getProperty(Constants.AUTH_URL);
			String moduleKey = env.getProperty(Constants.MODULE_KEY_LABEL);
			String authorization = Constants.BEARER + env.getProperty(Constants.VERIFY_TOKEN);
			logger.info("validating the user Authentication Token");
			if (authTokenCache.validateAuthToken(request, accessToken, authorization, moduleKey, url)) {
				TokenInfo tokenInfo = (TokenInfo) request.getAttribute("tokenInfo");
				boolean flag = isModuleSubscribed(tokenInfo);
				if (flag) {
					createTenantIfNotPresent(tokenInfo);
					if(tokenInfo.getUsername()!=null)
					{
					createUserIfNotPresent(accessToken, tokenInfo);
					chain.doFilter(httpServletRequest, httpServletResponse);
					}
					else {
						Message message = new Message();
						message.setId(403);
						message.setText("Please login into application");
						sendErrorResponse(response, message);
					}
				} else {
					Message message = new Message();
					message.setId(403);
					message.setText("You are not subscribed");
					sendErrorResponse(response,message);
				}
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	public void createUserIfNotPresent(String accessToken, TokenInfo tokenInfo) {

		boolean userExist = objectChecker.isUserExist(tokenInfo.getUsername(), tokenInfo.getTenantId());
		if (userExist) {
			logger.debug("User exist in table");
		} else {
			logger.debug("User not exist in table");
			User user = idnServer.getUser(accessToken, tokenInfo.getMember());
			Tenant tenant = objectChecker.getTenant(tokenInfo.getTenantId());
			user.setTenant(tenant);
			user.setTenantUserName(tokenInfo.getUsername());
			persister.persistUser(user);
		}
	}

	public void createTenantIfNotPresent(TokenInfo tokenInfo) {

		boolean tenantExist = objectChecker.isTenantExist(tokenInfo.getTenantId());
		if (!tenantExist) {
			Tenant tenant = new Tenant();
			tenant.setClientId(tokenInfo.getClientId());
			tenant.setExpirationTime(tokenInfo.getExpiration());
			tenant.setExpired(tokenInfo.isExpired());
			tenant.setTenantId(tokenInfo.getTenantId());
			persister.persistTenant(tenant);
		}
	}

	private void sendErrorResponse(ServletResponse response,Message message) throws IOException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.sendError(message.getId(), objectToJsonConverter(message));
	}

	public Boolean isModuleSubscribed(TokenInfo tokenInfo) {
		boolean flag = false;
		Modules[] module = tokenInfo.getModules();
		for (Modules m : module) {
			if (m.getUuid().equals(moduleToken)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * check if the user request needs login, based on the URL
	 * 
	 * @param request
	 * @return
	 */
	private Boolean needLogin(final HttpServletRequest httpServletRequest) {
		Boolean needed = Boolean.TRUE;
		String requestURI = httpServletRequest.getRequestURI();
		if (requestURI.indexOf("/visitor/") > 0) {
			needed = Boolean.FALSE;
		}
		return needed;
	}

	/**
	 * Method to proceed to the next element in the chain
	 * 
	 * @param request
	 * @param response
	 */
	/*
	 * private void processRequest(ServletRequest request, ServletResponse
	 * response, FilterChain chain) throws ServletException, IOException {
	 * chain.doFilter((HttpServletRequest) request, (HttpServletResponse)
	 * response); }
	 */

	public String objectToJsonConverter(Message response) {
		Gson gson = new Gson();
		return gson.toJson(response);
	}

}
