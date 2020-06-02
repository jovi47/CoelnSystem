package com.ifs.coeln.resources;

import java.net.URI;
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
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/tipos")
public class TipoResource {

	@Autowired
	private TipoService service;

	private void checkIfDeleted(Tipo obj, Long id) {
		if (obj.getIs_deleted() == true) {
			throw new ResourceNotFoundException("Tipo", id);
		}
	}

	@GetMapping
	public ResponseEntity<List<TipoDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<TipoDTO> findById(@PathVariable Long id) {
		Tipo obj = service.findById(id);
		checkIfDeleted(obj, id);
		return ResponseEntity.ok().body(new TipoDTO(obj));
	}

	@PostMapping
	public ResponseEntity<TipoDTO> insert(@RequestBody Tipo obj) {
		TipoDTO dto = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		checkIfDeleted(service.findById(id), id);
		verifyIfRelationAndDelete(id);
		return ResponseEntity.noContent().build();
	}

	private void verifyIfRelationAndDelete(Long id) {
		service.haveRelation(id);
		Tipo obj = new Tipo();
		obj.setIs_deleted(true);
		service.update(id, obj);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<TipoDTO> update(@PathVariable Long id, @RequestBody Tipo obj) {
		checkIfDeleted(service.findById(id), id);
		return ResponseEntity.ok().body(service.update(id, obj));
	}
}
