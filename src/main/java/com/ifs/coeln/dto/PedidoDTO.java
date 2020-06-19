package com.ifs.coeln.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.ifs.coeln.entities.Pedido;
import com.ifs.coeln.entities.Usuario;

public class PedidoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Instant data_entregue;
	private Instant data_devolucao;
	private Usuario usuario;
	private Set<PedidoComponenteDTO> items = new HashSet<>();

	public PedidoDTO(Pedido pedido) {
		this.id = pedido.getId();
		this.data_entregue = pedido.getData_entregue();
		this.data_devolucao = pedido.getData_devolucao();
		this.usuario = pedido.getUsuario();
		pedido.getItems().forEach(x -> items.add(new PedidoComponenteDTO(x)));
	}

	public Long getId() {
		return id;
	}

	public Instant getData_entregue() {
		return data_entregue;
	}

	public Instant getData_devolucao() {
		return data_devolucao;
	}

	public Object getUsuario() {
		if (usuario.getIs_servidor()) {
			return new ServidorDTO(usuario);
		} else {
			return new AlunoDTO(usuario);
		}
	}

	public Set<PedidoComponenteDTO> getItems() {
		return items;
	}

}
