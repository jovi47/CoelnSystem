package com.ifs.coeln.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ifs.coeln.dto.ComponenteDTO;
import com.ifs.coeln.entities.Componente;
import com.ifs.coeln.repositories.ComponenteRepository;
import com.ifs.coeln.services.exceptions.DatabaseException;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@Service
public class ComponenteService {

	@Autowired
	private TipoService tipoService;

	@Autowired
	ComponenteRepository repository;

	public List<ComponenteDTO> findAll() {
		return filterList(repository.findAll());
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

	public Componente findById(Long id) {
		Optional<Componente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Componente", id));
	}

	public ComponenteDTO insert(Componente obj) {
		Componente componente = new Componente(obj);
		componente = repository.save(componente);
		componente.setTipo(tipoService.findById(componente.getTipo().getId()));
		return new ComponenteDTO(componente);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Componente", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
		
	}	
	
	public ComponenteDTO update(Long id, Componente obj) {
		try {
			Componente entity = repository.getOne(id);
			updateData(entity, obj);
			return new ComponenteDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Componente", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "componente");
		}
	}

	private void updateData(Componente entity, Componente obj) {
		entity.setDescricao((obj.getDescricao() == null) ? entity.getDescricao() : obj.getDescricao());
		entity.setIs_deleted((obj.getIs_deleted() == null) ? entity.getIs_deleted() : obj.getIs_deleted());
		entity.setNome((obj.getNome() == null) ? entity.getNome() : obj.getNome());
		if (obj.getTipo() == null) {
		} else {
			entity.setTipo(tipoService.findById(obj.getTipo().getId()));
		}
	}
}
