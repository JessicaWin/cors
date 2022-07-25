package com.jessica.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jessica.controller.OriginTestType;

import lombok.extern.slf4j.Slf4j;

@WebFilter(urlPatterns = { "/cors/*" })
@Slf4j
public class CorsFilter implements Filter {
	private static final String ACCESS_CONTROL_ALLOW_HEADERS = "content-type,cookie,test";
	private static final String ACCESS_CONTROL_EXPOSE_HEADERS = "test";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String type = req.getParameter("type");
		if (type != null) {
			OriginTestType originTestType = OriginTestType.valueOf(type);
			if (OriginTestType.DIFFERENT_ORIGIN.equals(originTestType)) {
				res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://test.com");
			} else if (OriginTestType.NO_CREDENTIAL.equals(originTestType)) {
				res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, req.getHeader(HttpHeaders.ORIGIN));
			} else if (OriginTestType.FALSE_CREDENTIAL.equals(originTestType)) {
				res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, req.getHeader(HttpHeaders.ORIGIN));
				res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "false");
			} else if (OriginTestType.NOT_ALLOWED_HEADER.equals(originTestType)) {
				res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, req.getHeader(HttpHeaders.ORIGIN));
				res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
			} else if (OriginTestType.SUCCESS.equals(originTestType)) {
				res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, req.getHeader(HttpHeaders.ORIGIN));
				res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
				res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ACCESS_CONTROL_ALLOW_HEADERS);
				res.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, ACCESS_CONTROL_EXPOSE_HEADERS);
			}
		}
		res.addHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "300");
		if (RequestMethod.OPTIONS.name().equals(req.getMethod())) {
			res.setStatus(HttpStatus.OK.value());
			return;
		}
		chain.doFilter(request, response);
	}
}
