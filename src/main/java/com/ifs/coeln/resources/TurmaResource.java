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

import com.ifs.coeln.dto.TurmaDTO;
import com.ifs.coeln.entities.Turma;
import com.ifs.coeln.services.TurmaService;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/turmas")
public class TurmaResource {

	@Autowired
	private TurmaService service;

	private void checkIfDeleted(Turma obj, Long id) {
		if (obj.getIs_deleted() == true) {
			throw new ResourceNotFoundException("Tipo", id);
		}
	}

	@GetMapping
	public ResponseEntity<List<TurmaDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<TurmaDTO> findById(@PathVariable Long id) {
		Turma obj = service.findById(id);
		checkIfDeleted(obj, id);
		return ResponseEntity.ok().body(new TurmaDTO(obj));
	}

	@PostMapping
	public ResponseEntity<TurmaDTO> insert(@RequestBody Turma obj) {
		TurmaDTO dto = service.insert(obj);
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
		Turma obj = new Turma();
		obj.setIs_deleted(true);
		service.update(id, obj);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<TurmaDTO> update(@PathVariable Long id, @RequestBody Turma obj) {
		checkIfDeleted(service.findById(id), id);
		return ResponseEntity.ok().body(service.update(id, obj));
	}
}
