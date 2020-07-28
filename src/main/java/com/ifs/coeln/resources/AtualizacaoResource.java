package com.ifs.coeln.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifs.coeln.dto.AtualizacaoDTO;
import com.ifs.coeln.services.AtualizacaoService;

@RestController
@RequestMapping(value = "/atualizacao")
public class AtualizacaoResource {
	
	@Autowired
	private AtualizacaoService service;
	
	@GetMapping
	public ResponseEntity<List<AtualizacaoDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}
}
