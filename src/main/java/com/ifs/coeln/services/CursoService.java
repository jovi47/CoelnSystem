package com.ifs.coeln.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ifs.coeln.dto.CursoDTO;
import com.ifs.coeln.entities.Curso;
import com.ifs.coeln.repositories.CursoRepository;
import com.ifs.coeln.services.exceptions.DatabaseException;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@Service
public class CursoService {
	
	@Autowired
	CursoRepository repository;

	public List<CursoDTO> findAll() {
		return filterList(repository.findAll());
	}

	private List<CursoDTO> filterList(List<Curso> list) {
		List<CursoDTO> dto = new ArrayList<>();
		for (Curso curso : list) {
			if (curso.getIs_deleted() == false) {
				dto.add(new CursoDTO(curso));
			}
		}
		return dto;
	}

	public Curso findById(Long id) {
		Optional<Curso> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Curso", id));
	}

	public CursoDTO insert(Curso obj) {
		Curso tipo = new Curso(obj);
		repository.save(tipo);
		return new CursoDTO(tipo);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Curso", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public CursoDTO update(Long id, Curso obj) {
		try {
			Curso entity = repository.getOne(id);

			updateData(entity, obj);
			return new CursoDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Curso", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "tipo");
		}
	}

	private void updateData(Curso entity, Curso obj) {
		entity.setIs_deleted((obj.getIs_deleted() == null) ? entity.getIs_deleted() : obj.getIs_deleted());
		entity.setNome((obj.getNome() == null) ? entity.getNome() : obj.getNome());
	}
}
