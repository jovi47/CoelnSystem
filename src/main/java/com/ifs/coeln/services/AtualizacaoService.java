package com.ifs.coeln.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ifs.coeln.dto.AtualizacaoDTO;
import com.ifs.coeln.entities.Alteracao;
import com.ifs.coeln.entities.Atualizacao;
import com.ifs.coeln.entities.AtualizacaoAlteracao;
import com.ifs.coeln.repositories.AlteracaoRepository;
import com.ifs.coeln.repositories.AtualizacaoAlteracaoRepository;
import com.ifs.coeln.repositories.AtualizacaoRepository;
import com.ifs.coeln.services.exceptions.DatabaseException;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@Service
public class AtualizacaoService {

	@Autowired
	private AtualizacaoAlteracaoRepository atlAltRepository;
	@Autowired
	private AlteracaoRepository altRepository;
	@Autowired
	private AtualizacaoRepository repository;

	public List<AtualizacaoDTO> findAll() {
		return filterList(repository.findAll());
	}

	private List<AtualizacaoDTO> filterList(List<Atualizacao> list) {
		List<AtualizacaoDTO> dto = new ArrayList<>();
		for (Atualizacao atualizacao : list) {
			dto.add(new AtualizacaoDTO(atualizacao));
		}
		return dto;
	}

	public Atualizacao findById(Long id) {
		Optional<Atualizacao> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Atualizacao", id));
	}

	public AtualizacaoDTO insert(Atualizacao obj, List<Alteracao> alteracoes) {
		try {
			obj = repository.save(obj);
			for (Alteracao alteracao : alteracoes) {
				alteracao = altRepository.save(alteracao);
				AtualizacaoAlteracao atlAlt = new AtualizacaoAlteracao();
				atlAlt.setAtualizacao(obj);
				atlAlt.setAlteracao(alteracao);
				atlAltRepository.save(atlAlt);
			}
			return new AtualizacaoDTO(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "atualizacao");
		}
	}
}
