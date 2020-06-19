package com.ifs.coeln.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ifs.coeln.dto.PedidoDTO;
import com.ifs.coeln.entities.Componente;
import com.ifs.coeln.entities.Historico;
import com.ifs.coeln.entities.Pedido;
import com.ifs.coeln.entities.PedidoComponente;
import com.ifs.coeln.entities.Usuario;
import com.ifs.coeln.repositories.PedidoComponenteRepository;
import com.ifs.coeln.repositories.PedidoRepository;
import com.ifs.coeln.services.exceptions.DatabaseException;
import com.ifs.coeln.services.exceptions.ResourceNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private ComponenteService compService;
	@Autowired
	private PedidoComponenteRepository pcRepository;
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private HistoricoService hisService;

	@Autowired
	private PedidoRepository repository;

	public List<PedidoDTO> findAll() {
		return filterList(repository.findAll());
	}

	private List<PedidoDTO> filterList(List<Pedido> list) {
		List<PedidoDTO> dto = new ArrayList<>();
		for (Pedido pedido : list) {
			if (pedido.getIs_deleted() == false) {
				dto.add(new PedidoDTO(pedido));
			}
		}
		return dto;
	}

	public Pedido findById(Long id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Pedido", id));
	}

	public PedidoDTO insert(Pedido obj) {
		obj.getItems().forEach(x -> {
			Componente comp = compService.findById(x.getComponente().getId());
			if (comp.getIs_deleted()) {
				throw new ResourceNotFoundException("Componente", comp.getId());
			} else {
				x.setComponente(comp);
			}
		});
		try {
			Pedido pedido = new Pedido(obj);
			Usuario usuario = usuarioService.findByMatricula(pedido.getUsuario().getMatricula());
			if (usuario.getIs_deleted()) {
				throw new ResourceNotFoundException("Usuario", usuario.getMatricula());
			}
			pedido = repository.save(pedido);
			for (PedidoComponente pc : obj.getItems()) {
				pc.setPedido(pedido);
				pcRepository.save(pc);
			}
			pedido.setUsuario(usuario);
			hisService.insert(new Historico(null, "inserido", pedido.getId().toString(), "Pedido", 1L));
			return new PedidoDTO(pedido);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "componente");
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Pedido", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}

	}
	public void haveRelation(Long id) {
		try {
			Pedido entity = repository.getOne(id);
			if (entity.getItems().size() != 0) {
				throw new DatabaseException("Esse pedido possui relacao com outras tabelas, exclusao negada");
			}
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Pedido", id);
		}
	}

	public PedidoDTO update(Long id, Pedido obj) {
		try {
			Pedido entity = repository.getOne(id);
			updateData(entity, obj);
			if (entity.getIs_deleted()) {
				hisService.insert(new Historico(null, "deletado", entity.getId().toString(), "Pedido", 1L));
			}
			return new PedidoDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Pedido", id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e, "pedido");
		}
	}

	private void updateData(Pedido entity, Pedido obj) {
		entity.setData_devolucao(
				(obj.getData_devolucao() == null) ? entity.getData_devolucao() : obj.getData_devolucao());
		entity.setData_entregue((obj.getData_entregue() == null) ? entity.getData_entregue() : obj.getData_entregue());
		entity.setIs_deleted((obj.getIs_deleted() == null) ? entity.getIs_deleted() : obj.getIs_deleted());
		if (obj.getUsuario() == null) {
		} else {
			entity.setUsuario(usuarioService.findByMatricula(obj.getUsuario().getMatricula()));
		}
	}
}
