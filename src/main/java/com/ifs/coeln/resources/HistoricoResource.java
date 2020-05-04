package com.ifs.coeln.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifs.coeln.services.HistoricoService;

@RestController
@RequestMapping(value = "/historico")
public class HistoricoResource {
	
	@Autowired
	private HistoricoService service;
}
