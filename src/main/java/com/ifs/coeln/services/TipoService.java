package com.ifs.coeln.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ifs.coeln.dto.TipoDTO;
import com.ifs.coeln.entities.Tipo;
import com.ifs.coeln.repositories.TipoRepository;
import com.ifs.coeln.services.exceptions.DatabaseException;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@Service
public class TipoService {

	@Autowired
	TipoRepository repository;

	public List<TipoDTO> findAll() {
		return filterList(repository.findAll());
	}

	private List<TipoDTO> filterList(List<Tipo> list) {
		List<TipoDTO> dto = new ArrayList<>();
		for (Tipo componente : list) {
			if (componente.getIs_deleted() == false) {
				dto.add(new TipoDTO(componente));
			}
		}
		return dto;
	}

	public Tipo findById(Long id) {
		Optional<Tipo> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Tipo", id));
	}

	public TipoDTO insert(Tipo obj) {
		Tipo tipo = new Tipo(obj);
		repository.save(tipo);
		return new TipoDTO(tipo);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Tipo", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
		
	public void haveRelation(Long id) {
		try {
			Tipo entity = repository.getOne(id);
			if(entity.getComponentes().size()!=0) {
				throw new DatabaseException("Esse tipo possue relacao com outras tabelas, exclusao negada");
			}
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Tipo", id);
		}
	}
	public TipoDTO update(Long id, Tipo obj) {
		try {
			Tipo entity = repository.getOne(id);
			updateData(entity, obj);
			return new TipoDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Tipo", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "tipo");
		}
	}

	private void updateData(Tipo entity, Tipo obj) {
		entity.setIs_deleted((obj.getIs_deleted() == null) ? entity.getIs_deleted() : obj.getIs_deleted());
		entity.setNome((obj.getNome() == null) ? entity.getNome() : obj.getNome());
	}
}