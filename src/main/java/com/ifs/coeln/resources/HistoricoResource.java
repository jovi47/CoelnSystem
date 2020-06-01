package com.ifs.coeln.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifs.coeln.dto.HistoricoDTO;
import com.ifs.coeln.services.HistoricoService;

@RestController
@RequestMapping(value = "/historico")
public class HistoricoResource {
	
	@Autowired
	private HistoricoService service;
	
	@GetMapping
	public ResponseEntity<List<HistoricoDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}
}
