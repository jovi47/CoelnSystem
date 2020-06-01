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

import com.ifs.coeln.dto.LaboratorioDTO;
import com.ifs.coeln.entities.Laboratorio;
import com.ifs.coeln.services.LaboratorioService;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/laboratorios")
public class LaboratorioResource {

	@Autowired
	private LaboratorioService service;

	@GetMapping
	public ResponseEntity<List<LaboratorioDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<LaboratorioDTO> findById(@PathVariable Long id) {
		Laboratorio obj = service.findById(id);
		if (obj.getIs_deleted() == true) {
			throw new ResourceNotFoundException("Laboratorio", id);
		}
		return ResponseEntity.ok().body(new LaboratorioDTO(obj));
	}

	@PostMapping
	public ResponseEntity<LaboratorioDTO> insert(@RequestBody Laboratorio obj) {
		LaboratorioDTO dto = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (service.findById(id).getIs_deleted() == true) {
			throw new ResourceNotFoundException("Laboratorio", id);
		}
		service.haveRelation(id);
		Laboratorio obj = new Laboratorio();
		obj.setIs_deleted(true);
		service.update(id, obj);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<LaboratorioDTO> update(@PathVariable Long id, @RequestBody Laboratorio obj) {
		if (service.findById(id).getIs_deleted() == true) {
			throw new ResourceNotFoundException("Laboratorio", id);
		}
		return ResponseEntity.ok().body(service.update(id, obj));
	}
}
