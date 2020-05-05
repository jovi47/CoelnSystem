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

import com.ifs.coeln.dto.ComponenteDTO;
import com.ifs.coeln.entities.Componente;
import com.ifs.coeln.entities.Tipo;
import com.ifs.coeln.services.ComponenteService;
import com.ifs.coeln.services.TipoService;

@RestController
@RequestMapping(value = "/componentes")
public class ComponenteResource {

	@Autowired
	private ComponenteService service;

	@GetMapping
	public ResponseEntity<List<ComponenteDTO>> findAll() {
		List<ComponenteDTO> dto = filterList(service.findAll());
		return ResponseEntity.ok().body(dto);
	}

	private List<ComponenteDTO> filterList(List<Componente> list) {
		List<ComponenteDTO> dto = new ArrayList<>();
		for (Componente componente : list) {
			if (componente.getIs_deleted() != null && componente.getIs_deleted() == false) {
				dto.add(new ComponenteDTO(componente));
			}
		}
		return dto;
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Componente> findById(@PathVariable Long id) {
		Componente user = service.findById(id);
		return ResponseEntity.ok().body(user);
	}

	@Autowired
	private TipoService tipoService;

	@PostMapping
	public ResponseEntity<Componente> insert(@RequestBody Componente obj) {
		Componente componente = new Componente(obj);
		componente = service.insert(componente);
		componente.setTipo(tipoService.findById(componente.getTipo().getId()));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(componente.getId())
				.toUri();
		return ResponseEntity.created(uri).body(componente);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Componente> update(@PathVariable Long id, @RequestBody Componente obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}
