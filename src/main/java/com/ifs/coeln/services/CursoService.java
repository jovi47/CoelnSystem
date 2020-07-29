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
import com.ifs.coeln.entities.Alteracao;
import com.ifs.coeln.entities.Atualizacao;
import com.ifs.coeln.entities.Curso;
import com.ifs.coeln.entities.Historico;
import com.ifs.coeln.repositories.CursoRepository;
import com.ifs.coeln.services.exceptions.DatabaseException;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@Service
public class CursoService {

	@Autowired
	private AtualizacaoService atlService;
	@Autowired
	private HistoricoService hisService;

	@Autowired
	private CursoRepository repository;

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
		try {
			Curso curso = new Curso(obj);
			repository.save(curso);
			hisService.insert(new Historico(null, "inserido", curso.getId().toString(), "Curso", 1L));
			return new CursoDTO(curso);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "curso");
		}
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
			List<Alteracao> alteracoes = updateData(entity, obj);
			if (entity.getIs_deleted()) {
				hisService.insert(new Historico(null, "deletado", entity.getId().toString(), "Curso", 1L));
			} else {
				atlService.insert(new Atualizacao(null, entity.getId().toString(), "Curso", 1L), alteracoes);
			}
			return new CursoDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Curso", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "curso");
		}
	}

	private List<Alteracao> updateData(Curso entity, Curso obj) {
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
