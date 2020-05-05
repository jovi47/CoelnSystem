package com.ifs.coeln.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ifs.coeln.entities.Tipo;
import com.ifs.coeln.repositories.TipoRepository;
import com.joaozin.course.services.exceptions.DatabaseException;
import com.joaozin.course.services.exceptions.ResourceNotFoundException;

@Service
public class TipoService {

	@Autowired
	TipoRepository repository;
	
	public List<Tipo> findAll() {
		return repository.findAll();
	}

	public Tipo findById(Long id) {
		Optional<Tipo> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Tipo insert(Tipo obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public Tipo update(Long id, Tipo obj) {
		try {
			Tipo entity = repository.getOne(id);
			updateData(entity, obj);
			return repository.save(entity);			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Tipo entity, Tipo obj) {
		entity.setIs_deleted(obj.getIs_deleted());
		entity.setNome(obj.getNome());
	}
}
