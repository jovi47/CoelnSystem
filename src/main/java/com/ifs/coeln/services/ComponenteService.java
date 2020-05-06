package com.ifs.coeln.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ifs.coeln.entities.Componente;
import com.ifs.coeln.repositories.ComponenteRepository;
import com.joaozin.course.services.exceptions.DatabaseException;
import com.joaozin.course.services.exceptions.ResourceNotFoundException;

@Service
public class ComponenteService {

	@Autowired
	private TipoService tipoService;

	@Autowired
	ComponenteRepository repository;

	public List<Componente> findAll() {
		return repository.findAll();
	}

	public Componente findById(Long id) {
		Optional<Componente> obj = repository.findById(id);
			return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Componente insert(Componente obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public Componente update(Long id, Componente obj) {
		try {
			Componente entity = repository.getOne(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
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
