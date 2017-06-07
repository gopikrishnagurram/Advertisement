package com.wavelabs;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.wavelabs.model.Message;
import com.wavelabs.model.Modules;
import com.wavelabs.model.TokenInfo;

@Component
@Order(2)
public class SubscriptionValidationFilter implements Filter {

	private static final String moduleToken = "MOD:11296761-d4d8-4975-b27f-41090a607702";

	// MOD:11296761-d4d8-4975-b27f-41090a607702
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		boolean flag = false;
		TokenInfo tokenInfo = (TokenInfo) request.getAttribute("toeknInfo");
		Modules[] module = tokenInfo.getModules();
		for (Modules m : module) {
			if (m.getUuid().equals(moduleToken)) {
				flag = true;
				break;
			}
		}
		if (flag) {
			chain.doFilter(request, response);
		} else {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			Message message = new Message();
			message.setId(401);
			message.setText("You are not subscribed to this module");
			httpResponse.sendError(403, objectToJsonConverter(message));
		}
	}

	@Override
	public void destroy() {

	}

	public String objectToJsonConverter(Message response) {
		Gson gson = new Gson();
		return gson.toJson(response);
	}
}
