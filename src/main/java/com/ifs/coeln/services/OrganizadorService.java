package com.ifs.coeln.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ifs.coeln.dto.OrganizadorDTO;
import com.ifs.coeln.entities.Historico;
import com.ifs.coeln.entities.Laboratorio;
import com.ifs.coeln.entities.Organizador;
import com.ifs.coeln.repositories.OrganizadorRepository;
import com.ifs.coeln.services.exceptions.DatabaseException;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@Service
public class OrganizadorService {

	@Autowired
	private LaboratorioService labService;

	@Autowired
	private HistoricoService hisService;

	@Autowired
	private OrganizadorRepository repository;

	public List<OrganizadorDTO> findAll() {
		return filterList(repository.findAll());
	}

	private List<OrganizadorDTO> filterList(List<Organizador> list) {
		List<OrganizadorDTO> dto = new ArrayList<>();
		for (Organizador curso : list) {
			if (curso.getIs_deleted() == false) {
				dto.add(new OrganizadorDTO(curso));
			}
		}
		return dto;
	}

	public Organizador findById(Long id) {
		Optional<Organizador> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Organizador", id));
	}

	public OrganizadorDTO insert(Organizador obj) {
		try {
			Laboratorio lab = labService.findById(obj.getLaboratorio().getId());
			if (lab.getIs_deleted()) {
				throw new ResourceNotFoundException("Laboratorio", lab.getId());
			}
			Organizador org = new Organizador(obj);
			org.setLaboratorio(lab);
			repository.save(org);
			hisService.insert(new Historico(null, "inserido", org.getId().toString(), "Organizador", 1L));
			return new OrganizadorDTO(org);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "organizador");
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Organizador", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public void haveRelation(Long id) {
		try {
			Organizador entity = repository.getOne(id);
			if (entity.getItens().size() != 0) {
				throw new DatabaseException("Esse organizador possui relacao com outras tabelas, exclusao negada");
			}
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Organizador", id);
		}
	}

	public OrganizadorDTO update(Long id, Organizador obj) {
		try {
			Organizador entity = repository.getOne(id);
			updateData(entity, obj);
			if (entity.getIs_deleted()) {
				hisService.insert(new Historico(null, "deletado", entity.getId().toString(), "Organizador", 1L));
			}
			return new OrganizadorDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Organizador", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "organizador");
		}
	}

	private void updateData(Organizador entity, Organizador obj) {
		entity.setIs_deleted((obj.getIs_deleted() == null) ? entity.getIs_deleted() : obj.getIs_deleted());
		if (obj.getLaboratorio() == null) {
		} else {
			entity.setLaboratorio(labService.findById(obj.getLaboratorio().getId()));
		}
	}
}
