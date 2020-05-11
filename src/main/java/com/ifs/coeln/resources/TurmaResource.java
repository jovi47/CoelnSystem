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

	@GetMapping
	public ResponseEntity<List<TurmaDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<TurmaDTO> findById(@PathVariable Long id) {
		Turma tipo = service.findById(id);
		if (tipo.getIs_deleted()) {
			throw new ResourceNotFoundException("Turma", id);
		}
		return ResponseEntity.ok().body(new TurmaDTO(tipo));
	}

	@PostMapping
	public ResponseEntity<TurmaDTO> insert(@RequestBody Turma obj) {
		TurmaDTO dto = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (service.findById(id).getIs_deleted() == true) {
			throw new ResourceNotFoundException("Turma", id);
		}
		Turma turma = new Turma();
		turma.setIs_deleted(true);
		service.update(id, turma);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<TurmaDTO> update(@PathVariable Long id, @RequestBody Turma obj) {
		if (service.findById(id).getIs_deleted() == true) {
			throw new ResourceNotFoundException("Turma", id);
		}
		return ResponseEntity.ok().body(service.update(id, obj));
	}
}
