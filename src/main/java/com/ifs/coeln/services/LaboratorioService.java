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
import com.ifs.coeln.entities.Alteracao;
import com.ifs.coeln.entities.Atualizacao;
import com.ifs.coeln.entities.Historico;
import com.ifs.coeln.entities.Laboratorio;
import com.ifs.coeln.repositories.LaboratorioRepository;
import com.ifs.coeln.services.exceptions.DatabaseException;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@Service
public class LaboratorioService {

	@Autowired
	private AtualizacaoService atlService;
	@Autowired
	private HistoricoService hisService;

	@Autowired
	private LaboratorioRepository repository;

	public List<LaboratorioDTO> findAll() {
		return filterList(repository.findAll());
	}

	private List<LaboratorioDTO> filterList(List<Laboratorio> list) {
		List<LaboratorioDTO> dto = new ArrayList<>();
		for (Laboratorio laboratorio : list) {
			if (laboratorio.getIs_deleted() == false) {
				dto.add(new LaboratorioDTO(laboratorio));
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
			hisService.insert(new Historico(null, "inserido", lab.getId().toString(), "Laboratorio", 1L));
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

	public void haveRelation(Long id) {
		try {
			Laboratorio entity = repository.getOne(id);
			if (entity.getOrganizadores().size() != 0) {
				throw new DatabaseException("Esse laboratorio possui relacao com outras tabelas, exclusao negada");
			}
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Laboratorio", id);
		}
	}

	public LaboratorioDTO update(Long id, Laboratorio obj) {
		try {
			Laboratorio entity = repository.getOne(id);
			List<Alteracao> alteracoes = updateData(entity, obj);
			if (entity.getIs_deleted()) {
				hisService.insert(new Historico(null, "deletado", entity.getId().toString(), "Laboratorio", 1L));
			} else {
				atlService.insert(new Atualizacao(null, entity.getId().toString(), "Laboratorio", 1L), alteracoes);
			}
			return new LaboratorioDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("laboratorio", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "laboratorio");
		}
	}

	private List<Alteracao> updateData(Laboratorio entity, Laboratorio obj) {
		List<Alteracao> alteracoes = new ArrayList<>();
		entity.setIs_deleted((obj.getIs_deleted() == null) ? entity.getIs_deleted() : obj.getIs_deleted());
			//Não dá pra dar update no id lembrar.
		if (entity.getId() != null) {
			String id = entity.getId().toString();
			entity.setId(obj.getId());
			alteracoes.add(new Alteracao("Id", id, obj.getId().toString()));
		}
		return alteracoes;
	}

}
