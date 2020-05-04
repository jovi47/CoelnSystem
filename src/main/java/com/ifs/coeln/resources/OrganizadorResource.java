package com.ifs.coeln.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifs.coeln.services.OrganizadorService;

@RestController
@RequestMapping(value = "/organizadores")
public class OrganizadorResource {
	
	@Autowired
	private OrganizadorService service;
	
}
