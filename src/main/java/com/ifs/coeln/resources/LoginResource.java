package com.ifs.coeln.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifs.coeln.services.LoginService;

@RestController
@RequestMapping(value = "/logins")
public class LoginResource {
	
	@Autowired
	private LoginService service;
}
