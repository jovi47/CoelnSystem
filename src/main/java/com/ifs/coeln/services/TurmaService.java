package com.ifs.coeln.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ifs.coeln.dto.TurmaDTO;
import com.ifs.coeln.entities.Alteracao;
import com.ifs.coeln.entities.Atualizacao;
import com.ifs.coeln.entities.Historico;
import com.ifs.coeln.entities.Turma;
import com.ifs.coeln.repositories.TurmaRepository;
import com.ifs.coeln.services.exceptions.DatabaseException;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@Service
public class TurmaService {

	@Autowired
	private AtualizacaoService atlService;

	@Autowired
	private HistoricoService hisService;

	@Autowired
	private TurmaRepository repository;

	public List<TurmaDTO> findAll() {
		return filterList(repository.findAll());
	}

	private List<TurmaDTO> filterList(List<Turma> list) {
		List<TurmaDTO> dto = new ArrayList<>();
		for (Turma curso : list) {
			if (curso.getIs_deleted() == false) {
				dto.add(new TurmaDTO(curso));
			}
		}
		return dto;
	}

	public Turma findById(Long id) {
		Optional<Turma> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Turma", id));
	}

	public TurmaDTO insert(Turma obj) {
		try {
			Turma tipo = new Turma(obj);
			repository.save(tipo);
			hisService.insert(new Historico(null, "inserido", tipo.getId().toString(), "Turma", 1L));
			return new TurmaDTO(tipo);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "turma");
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Turma", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public TurmaDTO update(Long id, Turma obj) {
		try {
			Turma entity = repository.getOne(id);
			List<Alteracao> alteracoes = updateData(entity, obj);
			if (entity.getIs_deleted()) {
				hisService.insert(new Historico(null, "deletado", entity.getId().toString(), "Turma", 1L));
			} else {
				atlService.insert(new Atualizacao(null, entity.getId().toString(), "Turma", 1L), alteracoes);
			}
			return new TurmaDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Turma", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "turma");
		}
	}

	private List<Alteracao> updateData(Turma entity, Turma obj) {
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
