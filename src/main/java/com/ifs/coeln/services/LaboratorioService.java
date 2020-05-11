package com.ifs.coeln.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ifs.coeln.dto.LaboratorioDTO;
import com.ifs.coeln.entities.Laboratorio;
import com.ifs.coeln.repositories.LaboratorioRepository;
import com.ifs.coeln.services.exceptions.DatabaseException;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@Service
public class LaboratorioService {

	@Autowired
	private LaboratorioRepository repository;

	public List<LaboratorioDTO> findAll() {
		return filterList(repository.findAll());
	}

	private List<LaboratorioDTO> filterList(List<Laboratorio> list) {
		List<LaboratorioDTO> dto = new ArrayList<>();
		for (Laboratorio curso : list) {
			if (curso.getIs_deleted() == false) {
				dto.add(new LaboratorioDTO(curso));
			}
		}
		return dto;
	}

	public Laboratorio findById(Long id) {
		Optional<Laboratorio> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Laboratorio", id));
	}

	public LaboratorioDTO insert(Laboratorio obj) {
		try {
			Laboratorio lab = new Laboratorio(obj);
			repository.save(lab);
			return new LaboratorioDTO(lab);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "laboratorio");
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Laboratorio", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public LaboratorioDTO update(Long id, Laboratorio obj) {
		try {
			Laboratorio entity = repository.getOne(id);
			updateData(entity, obj);
			return new LaboratorioDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("laboratorio", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "laboratorio");
		}
	}

	private void updateData(Laboratorio entity, Laboratorio obj) {
		entity.setId((obj.getId() == null) ? entity.getId() : obj.getId());
		entity.setIs_deleted((obj.getIs_deleted() == null) ? entity.getIs_deleted() : obj.getIs_deleted());
	}

	public void haveRelation(Long id) {
		try {
			Laboratorio entity = repository.getOne(id);
			if (entity.getOrganizadores().size() != 0) {
				throw new DatabaseException("Esse tipo possue relacao com outras tabelas, exclusao negada");
			}
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Laboratorio", id);
		}
	}
}
