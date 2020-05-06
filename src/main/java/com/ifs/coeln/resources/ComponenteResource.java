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
import com.ifs.coeln.services.ComponenteService;
import com.ifs.coeln.services.TipoService;
import com.joaozin.course.services.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/componentes")
public class ComponenteResource {

	@Autowired
	private TipoService tipoService;

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
			if (componente.getIs_deleted() == false) {
				dto.add(new ComponenteDTO(componente));
			}
		}
		return dto;
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ComponenteDTO> findById(@PathVariable Long id) {
		Componente comp = service.findById(id);
		if (comp.getIs_deleted() == true) {
			throw new ResourceNotFoundException(id);
		}
		ComponenteDTO componente = new ComponenteDTO(comp);
		return ResponseEntity.ok().body(componente);
	}

	@PostMapping
	public ResponseEntity<ComponenteDTO> insert(@RequestBody Componente obj) {
		ComponenteDTO dto = insertAndSetTipo(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	private ComponenteDTO insertAndSetTipo(Componente obj) {
		Componente componente = new Componente(obj);
		componente = service.insert(componente);
		componente.setTipo(tipoService.findById(componente.getTipo().getId()));
		return new ComponenteDTO(componente);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (service.findById(id).getIs_deleted() == true) {
			throw new ResourceNotFoundException(id);
		}
		Componente comp = new Componente();
		comp.setIs_deleted(true);
		service.update(id, comp);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ComponenteDTO> update(@PathVariable Long id, @RequestBody Componente obj) {
		if (service.findById(id).getIs_deleted() == true) {
			throw new ResourceNotFoundException(id);
		}
		obj = service.update(id, obj);
		ComponenteDTO dto = new ComponenteDTO(obj);
		return ResponseEntity.ok().body(dto);
	}
}
