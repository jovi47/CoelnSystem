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
import com.ifs.coeln.entities.Alteracao;
import com.ifs.coeln.entities.Atualizacao;
import com.ifs.coeln.entities.Historico;
import com.ifs.coeln.entities.Tipo;
import com.ifs.coeln.repositories.TipoRepository;
import com.ifs.coeln.services.exceptions.DatabaseException;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@Service
public class TipoService {
	
	@Autowired 
	private AtualizacaoService atlService;
	@Autowired
	private HistoricoService hisService;
	
	@Autowired
	private TipoRepository repository;

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
		try {
		Tipo tipo = new Tipo(obj);
		repository.save(tipo);
		hisService.insert(new Historico(null, "inserido", tipo.getId().toString(), "Tipo", 1L));
		return new TipoDTO(tipo);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "tipo");
		}
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
				throw new DatabaseException("Esse tipo possui relacao com outras tabelas, exclusao negada");
			}
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Tipo", id);
		}
	}
	public TipoDTO update(Long id, Tipo obj) {
		try {
			Tipo entity = repository.getOne(id);
			List<Alteracao> alteracoes = updateData(entity, obj);
			if(entity.getIs_deleted()) {
				hisService.insert(new Historico(null, "deletado", entity.getId().toString(), "Tipo", 1L));
			} else {
				atlService.insert(new Atualizacao(null, entity.getId().toString(), "Tipo", 1L), alteracoes);
			}
			return new TipoDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Tipo", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "tipo");
		}
	}

	private List<Alteracao> updateData(Tipo entity, Tipo obj) {
		entity.setIs_deleted((obj.getIs_deleted() == null) ? entity.getIs_deleted() : obj.getIs_deleted());
		List<Alteracao> alteracoes = new ArrayList<>();
		if (obj.getNome() != null) {
			String nome = entity.getNome();
			entity.setNome(obj.getNome());
			alteracoes.add(new Alteracao("Nome", nome, obj.getNome()));
		}
		return alteracoes;
	}
}