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

import com.ifs.coeln.dto.CursoDTO;
import com.ifs.coeln.entities.Curso;
import com.ifs.coeln.services.CursoService;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/cursos")
public class CursoResource {

	@Autowired
	private CursoService service;

	@GetMapping
	public ResponseEntity<List<CursoDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CursoDTO> findById(@PathVariable Long id) {
		Curso tipo = service.findById(id);
		if (tipo.getIs_deleted() == true) {
			throw new ResourceNotFoundException("Curso", id);
		}
		return ResponseEntity.ok().body(new CursoDTO(tipo));
	}

	@PostMapping
	public ResponseEntity<CursoDTO> insert(@RequestBody Curso obj) {
		CursoDTO dto = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (service.findById(id).getIs_deleted() == true) {
			throw new ResourceNotFoundException("Curso", id);
		}
		Curso tipo = new Curso();
		tipo.setIs_deleted(true);
		service.update(id, tipo);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<CursoDTO> update(@PathVariable Long id, @RequestBody Curso obj) {
		if (service.findById(id).getIs_deleted() == true) {
			throw new ResourceNotFoundException("Curso", id);
		}
		return ResponseEntity.ok().body(service.update(id, obj));
	}
}
