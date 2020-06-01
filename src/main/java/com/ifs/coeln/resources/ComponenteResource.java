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

import com.ifs.coeln.dto.ComponenteDTO;
import com.ifs.coeln.entities.Componente;
import com.ifs.coeln.services.ComponenteService;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/componentes")
public class ComponenteResource {

	@Autowired
	private ComponenteService service;

	@GetMapping
	public ResponseEntity<List<ComponenteDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ComponenteDTO> findById(@PathVariable Long id) {
		Componente obj = service.findById(id);
		if (obj.getIs_deleted() == true) {
			throw new ResourceNotFoundException("Componente", id);
		}
		return ResponseEntity.ok().body(new ComponenteDTO(obj));
	}

	@PostMapping
	public ResponseEntity<ComponenteDTO> insert(@RequestBody Componente obj) {
		ComponenteDTO dto = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (service.findById(id).getIs_deleted() == true) {
			throw new ResourceNotFoundException("Componente", id);
		}
		service.haveRelation(id);
		Componente obj = new Componente();
		obj.setIs_deleted(true);
		service.update(id, obj);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ComponenteDTO> update(@PathVariable Long id, @RequestBody Componente obj) {
		if (service.findById(id).getIs_deleted() == true) {
			throw new ResourceNotFoundException("Componente", id);
		}
		return ResponseEntity.ok().body(service.update(id, obj));
	}
}
