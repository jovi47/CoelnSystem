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
import com.ifs.coeln.dto.TipoDTO;
import com.ifs.coeln.entities.Alteracao;
import com.ifs.coeln.entities.Atualizacao;
import com.ifs.coeln.entities.Componente;
import com.ifs.coeln.entities.Historico;
import com.ifs.coeln.entities.Tipo;
import com.ifs.coeln.repositories.ComponenteRepository;
import com.ifs.coeln.services.exceptions.DatabaseException;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@Service
public class ComponenteService {

	@Autowired
	private TipoService tipoService;

	@Autowired
	private HistoricoService hisService;

	@Autowired
	private AtualizacaoService atlService;
	@Autowired
	private ComponenteRepository repository;

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

	public void haveRelation(Long id) {
		try {
			Componente entity = repository.getOne(id);
			if (entity.getObservacoes().size() != 0 || entity.getItens().size() != 0) {
				throw new DatabaseException("Esse componente possui relacao com outras tabelas, exclusao negada");
			}
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Componente", id);
		}
	}

	public ComponenteDTO insert(Componente obj) {
		try {
			Componente componente = new Componente(obj);
			Tipo tipo = tipoService.findById(componente.getTipo().getId());
			if (tipo.getIs_deleted()) {
				throw new ResourceNotFoundException("Tipo", tipo.getId());
			}
			componente = repository.save(componente);
			componente.setTipo(tipo);
			hisService.insert(new Historico(null, "inserido", componente.getId().toString(), "Componente", 1L));
			return new ComponenteDTO(componente);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "componente");
		}
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
			List<Alteracao> alteracoes = updateData(entity, obj);
			if (entity.getIs_deleted()) {
				hisService.insert(new Historico(null, "deletado", entity.getId().toString(), "Componente", 1L));
			} else {
				atlService.insert(new Atualizacao(null, entity.getId().toString(), "Componente", 1L), alteracoes);
			}
			return new ComponenteDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Componente", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "componente");
		}
	}

	private List<Alteracao> updateData(Componente entity, Componente obj) {
		entity.setIs_deleted((obj.getIs_deleted() == null) ? entity.getIs_deleted() : obj.getIs_deleted());
		List<Alteracao> alteracoes = new ArrayList<>();
		if (obj.getDescricao() != null) {
			String descricao = entity.getDescricao();
			entity.setDescricao(obj.getDescricao());
			alteracoes.add(new Alteracao("Descrição", descricao, obj.getDescricao()));
		}
		if (obj.getNome() != null) {
			String nome = entity.getNome();
			entity.setNome(obj.getNome());
			alteracoes.add(new Alteracao("Nome", nome, obj.getNome()));
		}
		if (obj.getTipo() != null) {
			Tipo tipo = tipoService.findById(entity.getTipo().getId());
			entity.setTipo(tipoService.findById(obj.getTipo().getId()));
			alteracoes
					.add(new Alteracao("Tipo", new TipoDTO(tipo).toString(), new TipoDTO(entity.getTipo()).toString()));
		} else {
			entity.setTipo(tipoService.findById(entity.getTipo().getId()));
		}
		return alteracoes;
	}
}
