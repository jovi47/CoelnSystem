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

import com.ifs.coeln.dto.PedidoDTO;
import com.ifs.coeln.entities.Pedido;
import com.ifs.coeln.services.PedidoService;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService service;

	private void checkIfDeleted(Pedido obj, Long id) {
		if (obj.getIs_deleted() == true) {
			throw new ResourceNotFoundException("Pedido", id);
		}
	}

	@GetMapping
	public ResponseEntity<List<PedidoDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<PedidoDTO> findById(@PathVariable Long id) {
		Pedido obj = service.findById(id);
		checkIfDeleted(obj, id);
		return ResponseEntity.ok().body(new PedidoDTO(obj));
	}

	@PostMapping
	public ResponseEntity<PedidoDTO> insert(@RequestBody Pedido obj) {
		PedidoDTO dto = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		checkIfDeleted(service.findById(id), id);
		verifyRelationAndDelete(id);
		return ResponseEntity.noContent().build();
	}

	private void verifyRelationAndDelete(Long id) {
		service.haveRelation(id);
		Pedido obj = new Pedido();
		obj.setIs_deleted(true);
		service.update(id, obj);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<PedidoDTO> update(@PathVariable Long id, @RequestBody Pedido obj) {
		checkIfDeleted(service.findById(id), id);
		return ResponseEntity.ok().body(service.update(id, obj));
	}
}
