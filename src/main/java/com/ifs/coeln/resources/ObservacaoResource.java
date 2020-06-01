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

import com.ifs.coeln.dto.ObservacaoDTO;
import com.ifs.coeln.entities.Observacao;
import com.ifs.coeln.services.ObservacaoService;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/observacoes")
public class ObservacaoResource {
	
	@Autowired
	private ObservacaoService service;

	@GetMapping
	public ResponseEntity<List<ObservacaoDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ObservacaoDTO> findById(@PathVariable Long id) {
		Observacao obj = service.findById(id);
		if (obj.getIs_deleted()) {
			throw new ResourceNotFoundException("Observacao", id);
		}
		return ResponseEntity.ok().body(new ObservacaoDTO(obj));
	}

	@PostMapping
	public ResponseEntity<ObservacaoDTO> insert(@RequestBody Observacao obj) {
		ObservacaoDTO dto = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (service.findById(id).getIs_deleted() == true) {
			throw new ResourceNotFoundException("Observacao", id);
		}
		Observacao obj = new Observacao();
		obj.setIs_deleted(true);
		service.update(id, obj);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ObservacaoDTO> update(@PathVariable Long id, @RequestBody Observacao obj) {
		if (service.findById(id).getIs_deleted() == true) {
			throw new ResourceNotFoundException("Observacao", id);
		}
		return ResponseEntity.ok().body(service.update(id, obj));
	}
	
}
