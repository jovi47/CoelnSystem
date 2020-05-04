package com.ifs.coeln.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifs.coeln.services.ObservacaoService;

@RestController
@RequestMapping(value = "/observacoes")
public class ObservacaoResource {
	
	@Autowired
	private ObservacaoService service;
	
}
