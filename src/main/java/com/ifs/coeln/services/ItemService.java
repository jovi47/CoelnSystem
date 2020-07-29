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
import com.ifs.coeln.dto.ItemDTO;
import com.ifs.coeln.dto.OrganizadorDTO;
import com.ifs.coeln.entities.Alteracao;
import com.ifs.coeln.entities.Atualizacao;
import com.ifs.coeln.entities.Componente;
import com.ifs.coeln.entities.Historico;
import com.ifs.coeln.entities.Item;
import com.ifs.coeln.entities.Organizador;
import com.ifs.coeln.repositories.ItemRepository;
import com.ifs.coeln.services.exceptions.DatabaseException;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@Service
public class ItemService {

	@Autowired
	private AtualizacaoService atlService;
	@Autowired
	private HistoricoService hisService;

	@Autowired
	private OrganizadorService orgService;

	@Autowired
	private ComponenteService compService;

	@Autowired
	private ItemRepository repository;

	public List<ItemDTO> findAll() {
		return filterList(repository.findAll());
	}

	private List<ItemDTO> filterList(List<Item> list) {
		List<ItemDTO> dto = new ArrayList<>();
		for (Item item : list) {
			if (item.getIs_deleted() == false) {
				dto.add(new ItemDTO(item));
			}
		}
		return dto;
	}

	public Item findById(Long id) {
		Optional<Item> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Item", id));
	}

	public ItemDTO insert(Item obj) {
		try {
			Item item = new Item(obj);
			Componente componente = compService.findById(obj.getComponente().getId());
			if (componente.getIs_deleted()) {
				throw new ResourceNotFoundException("Componente", componente.getId());
			}
			Organizador organizador = orgService.findById(obj.getOrganizador().getId());
			if (organizador.getIs_deleted()) {
				throw new ResourceNotFoundException("Organizador", organizador.getId());
			}
			item = repository.save(item);
			item.setComponente(componente);
			item.setOrganizador(organizador);
			hisService.insert(new Historico(null, "inserido", item.getId().toString(), "Item", 1L));
			return new ItemDTO(item);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "item");
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Item", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}

	}

	public ItemDTO update(Long id, Item obj) {
		try {
			Item entity = repository.getOne(id);
			List<Alteracao> alteracoes = updateData(entity, obj);
			if (entity.getIs_deleted()) {
				hisService.insert(new Historico(null, "deletado", entity.getId().toString(), "Item", 1L));
			}else {
				atlService.insert(new Atualizacao(null, entity.getId().toString(), "Item", 1L), alteracoes);
			}
			return new ItemDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Item", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "item");
		}
	}

	private List<Alteracao> updateData(Item entity, Item obj) {
		entity.setIs_deleted((obj.getIs_deleted() == null) ? entity.getIs_deleted() : obj.getIs_deleted());
		List<Alteracao> alteracoes = new ArrayList<>();
		if (obj.getQuantidade() != null) {
			Integer quantidade = entity.getQuantidade();
			entity.setQuantidade(obj.getQuantidade());
			alteracoes.add(new Alteracao("Nome", quantidade.toString(), obj.getQuantidade().toString()));
		}

		if (obj.getComponente() != null) {
			Componente componente = compService.findById(entity.getComponente().getId());
			entity.setComponente(compService.findById(obj.getComponente().getId()));
			alteracoes.add(new Alteracao("Componente", new ComponenteDTO(componente).toString(),
					new ComponenteDTO(entity.getComponente()).toString()));
		} else {
			entity.setComponente(compService.findById(entity.getComponente().getId()));
		}
		if (obj.getOrganizador() != null) {
			Organizador organizador = orgService.findById(entity.getOrganizador().getId());
			entity.setOrganizador(orgService.findById(obj.getOrganizador().getId()));
			alteracoes.add(new Alteracao("Organizador", new OrganizadorDTO(organizador).toString(),
					new OrganizadorDTO(entity.getOrganizador()).toString()));
		} else {
			entity.setOrganizador(orgService.findById(entity.getOrganizador().getId()));
		}
		return alteracoes;
	}
}
