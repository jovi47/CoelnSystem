package com.ifs.coeln.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ifs.coeln.dto.TipoDTO;
import com.ifs.coeln.entities.Tipo;
import com.ifs.coeln.services.TipoService;

@RestController
@RequestMapping(value = "/tipos")
public class TipoResource {
	
	@Autowired
	private TipoService service;
	

	@GetMapping
	public ResponseEntity<List<TipoDTO>> findAll() {
		List<TipoDTO> dto = filterList(service.findAll());
		return ResponseEntity.ok().body(dto);
	}

	private List<TipoDTO> filterList(List<Tipo> list) {
		List<TipoDTO> dto = new ArrayList<>();
		for (Tipo tipo : list) {
			if (tipo.getIs_deleted() != null && tipo.getIs_deleted() == false) {
				dto.add(new TipoDTO(tipo));
			}
		}
		return dto;
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Tipo> findById(@PathVariable Long id) {
		Tipo user = service.findById(id);
		return ResponseEntity.ok().body(user);
	}

	@PostMapping
	public ResponseEntity<Tipo> insert(@RequestBody Tipo obj) {
		Tipo tipo = new Tipo(obj);
		tipo = service.insert(tipo);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tipo.getId())
				.toUri();
		return ResponseEntity.created(uri).body(tipo);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Tipo> update(@PathVariable Long id, @RequestBody Tipo obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}
