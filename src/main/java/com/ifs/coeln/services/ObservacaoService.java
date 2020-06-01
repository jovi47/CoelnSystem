package com.ifs.coeln.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ifs.coeln.dto.ObservacaoDTO;
import com.ifs.coeln.entities.Componente;
import com.ifs.coeln.entities.Historico;
import com.ifs.coeln.entities.Observacao;
import com.ifs.coeln.repositories.ObservacaoRepository;
import com.ifs.coeln.services.exceptions.DatabaseException;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@Service
public class ObservacaoService {
	
	@Autowired
	private HistoricoService hisService;
	
	@Autowired
	private ComponenteService compService;
	
	@Autowired
	private ObservacaoRepository repository;

	public List<ObservacaoDTO> findAll() {
		return filterList(repository.findAll());
	}

	private List<ObservacaoDTO> filterList(List<Observacao> list) {
		List<ObservacaoDTO> dto = new ArrayList<>();
		for (Observacao curso : list) {
			if (curso.getIs_deleted() == false) {
				dto.add(new ObservacaoDTO(curso));
			}
		}
		return dto;
	}

	public Observacao findById(Long id) {
		Optional<Observacao> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Observacao", id));
	}

	public ObservacaoDTO insert(Observacao obj) {
		try {
			Componente comp = compService.findById(obj.getComponente().getId());
			if (comp.getIs_deleted()) {
				throw new ResourceNotFoundException("Componente", comp.getId());
			}
			Observacao org = new Observacao(obj);
			org.setComponente(comp);
			repository.save(org);
			hisService.insert(new Historico(null, "inserido", org.getId().toString(), "Observacao", 1L));
			return new ObservacaoDTO(org);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "observacao");
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Observacao", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public ObservacaoDTO update(Long id, Observacao obj) {
		try {
			Observacao entity = repository.getOne(id);
			updateData(entity, obj);
			hisService.insert(new Historico(null, "deletado", entity.getId().toString(), "Observacao", 1L));
			return new ObservacaoDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Observacao", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "observacao");
		}
	}

	private void updateData(Observacao entity, Observacao obj) {
		entity.setIs_deleted((obj.getIs_deleted() == null) ? entity.getIs_deleted() : obj.getIs_deleted());
		entity.setTexto((obj.getTexto() == null) ? entity.getTexto() : obj.getTexto());
		if (obj.getComponente() == null) {
		} else {
			entity.setComponente(compService.findById(obj.getComponente().getId()));
		}
	}
}
