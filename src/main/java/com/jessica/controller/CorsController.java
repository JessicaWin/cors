package com.jessica.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cors")
@Slf4j
public class CorsController {

	@RequestMapping(path = "/test", method = RequestMethod.GET)
	public String test(@RequestParam(name = "type") OriginTestType type,
			@RequestParam(name = "test", required = false) String test, HttpServletResponse res) {
		res.addHeader("test", "test-header");
		return "test";
	}

	@RequestMapping(path = "/test2", method = RequestMethod.GET)
	public String simple(@RequestParam(name = "type") OriginTestType type, HttpServletResponse res) {
		res.addHeader("test", "test-header");
		return "test";
	}

}
