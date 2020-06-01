package com.ifs.coeln.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ifs.coeln.dto.HistoricoDTO;
import com.ifs.coeln.entities.Historico;
import com.ifs.coeln.repositories.HistoricoRepository;
import com.ifs.coeln.services.exceptions.DatabaseException;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@Service
public class HistoricoService {

	@Autowired
	HistoricoRepository repository;

	public List<HistoricoDTO> findAll() {
		return filterList(repository.findAll());
	}

	private List<HistoricoDTO> filterList(List<Historico> list) {
		List<HistoricoDTO> dto = new ArrayList<>();
		for (Historico historico : list) {
			dto.add(new HistoricoDTO(historico));
		}
		return dto;
	}

	public Historico findById(Long id) {
		Optional<Historico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Historico", id));
	}

	public HistoricoDTO insert(Historico obj) {
		try {
			System.out.println(obj);
			obj = repository.save(obj);
			return new HistoricoDTO(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "historico");
		}
	}
}
