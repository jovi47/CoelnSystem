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

import com.ifs.coeln.dto.ServidorDTO;
import com.ifs.coeln.entities.Usuario;
import com.ifs.coeln.services.UsuarioService;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;
@RestController
@RequestMapping(value = "/servidores")
public class ServidorResource {
	@Autowired
	private UsuarioService service;

	private void checkIfDeleted(Usuario obj, String id) {
		if (obj.getIs_deleted() || !obj.getIs_servidor()) {
			throw new ResourceNotFoundException("Servidor", id);
		}
	}

	@GetMapping
	public ResponseEntity<List<ServidorDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAllServidores());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ServidorDTO> findById(@PathVariable String id) {
		Usuario obj = service.findByMatricula(id);
		checkIfDeleted(obj, id);
		return ResponseEntity.ok().body(new ServidorDTO(obj));
	}

	@PostMapping
	public ResponseEntity<ServidorDTO> insert(@RequestBody Usuario obj) {
		ServidorDTO dto = service.insertServidor(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getMatricula())
				.toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		checkIfDeleted(service.findByMatricula(id), id);
		verifyRelationAndDelete(id);
		return ResponseEntity.noContent().build();
	}

	private void verifyRelationAndDelete(String id) {
		Usuario obj = new Usuario();
		obj.setIs_deleted(true);
		service.updateServidor(id, obj);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ServidorDTO> update(@PathVariable String id, @RequestBody Usuario obj) {
		checkIfDeleted(service.findByMatricula(id), id);
		return ResponseEntity.ok().body(service.updateServidor(id, obj));
	}
}
