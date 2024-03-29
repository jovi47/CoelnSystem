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

import com.ifs.coeln.dto.ItemDTO;
import com.ifs.coeln.entities.Item;
import com.ifs.coeln.services.ItemService;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/itens")
public class ItemResource {

	@Autowired
	private ItemService service;
	
	private void checkIfDeleted(Item obj, Long id) {
		if (obj.getIs_deleted() == true) {
			throw new ResourceNotFoundException("Item", id);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<ItemDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ItemDTO> findById(@PathVariable Long id) {
		Item obj = service.findById(id);
		checkIfDeleted(obj, id);
		return ResponseEntity.ok().body(new ItemDTO(obj));
	}

	@PostMapping
	public ResponseEntity<ItemDTO> insert(@RequestBody Item obj) {
		ItemDTO dto = service.insert(obj);
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
		Item obj = new Item();
		obj.setIs_deleted(true);
		service.update(id, obj);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ItemDTO> update(@PathVariable Long id, @RequestBody Item obj) {
		checkIfDeleted(service.findById(id), id);
		return ResponseEntity.ok().body(service.update(id, obj));
	}
}
