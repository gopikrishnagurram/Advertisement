package com.wavelabs;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.DispatcherServlet;

import com.wavelabs.idn.IdnServerAPI;
import com.wavelabs.persist.Persister;
import com.wavelabs.record.existence.ObjectChecker;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan({ "com.wavelabs.resource", "com.wavelabs.service", "com.wavelabs.dao", "com.wavelabs.solr.service" })
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CacheManager cacheManager() {
		GuavaCacheManager cacheManager = new GuavaCacheManager("greetings");
		return cacheManager;
	}

	/**
	 * 
	 * {@link ServletRegistrationBean servletRegistrationBean} is class with a
	 * spring bean friendly design that is used to register servlets in a
	 * servlet 3.0 container within spring boot. Register dispatcherServlet
	 * programmatically
	 * 
	 * @return ServletRegistrationBean
	 */
	@Bean
	public ServletRegistrationBean dispatcherServletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet(), "/*");
		registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
		return registration;
	}

	/**
	 * To add your own servlet and map it to the root resource. Default would be
	 * root of your application(/)
	 * 
	 * @return DispatcherServlet
	 */
	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}

	/**
	 * Method to set the order for the filters
	 * 
	 * @return
	 */
	@Bean
	public Filter springFilter() {
		return new NbosFilter();
	}

	/*
	 * @Bean public Filter subscriptionValidationFilter() {
	 * 
	 * return new SubscriptionValidationFilter(); }
	 */

	@Bean
	public AuthenticationTokenCache authenticationTokenCache() {
		return new AuthenticationTokenCache();
	}

	@Bean
	public ObjectChecker objectChecker() {
		return new ObjectChecker();
	}

	@Bean
	public Persister persister() {
		return new Persister();
	}

	@Bean
	public IdnServerAPI IdnServerAPI() {
		return new IdnServerAPI();
	}

	// @Bean
	// public FilterRegistrationBean corsFilter() {
	// UrlBasedCorsConfigurationSource source = new
	// UrlBasedCorsConfigurationSource();
	// CorsConfiguration config = new CorsConfiguration();
	// config.setAllowCredentials(true);
	// config.addAllowedOrigin("http://domain1.com");
	// config.addAllowedHeader("*");
	// config.addAllowedMethod("*");
	// source.registerCorsConfiguration("/**", config);
	// FilterRegistrationBean bean = new FilterRegistrationBean(new
	// CorsFilter(source));
	// bean.setOrder(0);
	// return bean;
	// }
	//
	// @Configuration
	// @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	// protected static class SecurityConfiguration extends
	// WebSecurityConfigurerAdapter {
	//
	//// @Autowired
	//// private AuthenticationDAO authDAO;
	//// @Autowired
	//// private MemberDAO memberDAO;
	////
	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
	// http.httpBasic().and().authorizeRequests()
	// .antMatchers("/index.html", "/home.html", "/login.html", "/").permitAll()
	// .and().logout().and().csrf()
	// .csrfTokenRepository(csrfTokenRepository()).and()
	// .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
	// }
	//
	// private Filter csrfHeaderFilter() {
	// return new OncePerRequestFilter() {
	// @Override
	// protected void doFilterInternal(HttpServletRequest request,
	// HttpServletResponse response, FilterChain filterChain)
	// throws ServletException, IOException {
	// String authToken = request.getHeader("X-Auth-Token");
	// if(authToken != null){
	//// Authentication authentication = authDAO.findByAuthToken(authToken);
	//// loggedInMember = memberDAO.findByAuthentication(authentication);
	// }
	//
	// CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
	// .getName());
	// if (csrf != null) {
	// Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
	// String token = csrf.getToken();
	// if (cookie == null || token != null
	// && !token.equals(cookie.getValue())) {
	// cookie = new Cookie("XSRF-TOKEN", token);
	// cookie.setPath("/");
	// response.addCookie(cookie);
	// }
	// }
	// filterChain.doFilter(request, response);
	// }
	// };
	// }
	//
	// private CsrfTokenRepository csrfTokenRepository() {
	// HttpSessionCsrfTokenRepository repository = new
	// HttpSessionCsrfTokenRepository();
	// repository.setHeaderName("X-XSRF-TOKEN");
	// return repository;
	// }
	//
	//
	// }

	// @Configuration
	// public class MyConfiguration {
	//
	// @Bean
	// public FilterRegistrationBean corsFilter() {
	// UrlBasedCorsConfigurationSource source = new
	// UrlBasedCorsConfigurationSource();
	// CorsConfiguration config = new CorsConfiguration();
	// config.setAllowCredentials(true);
	// config.addAllowedOrigin("http://domain1.com");
	// config.addAllowedHeader("*");
	// config.addAllowedMethod("*");
	// source.registerCorsConfiguration("/**", config);
	// FilterRegistrationBean bean = new FilterRegistrationBean(new
	// CorsFilter(source));
	// bean.setOrder(0);
	// return bean;
	// }
	// }
}
