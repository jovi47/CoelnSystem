package com.ifs.coeln.dto;

import java.io.Serializable;

import com.ifs.coeln.entities.PedidoComponente;

public class PedidoComponenteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer quantidade;
	private ComponenteDTO componente;

	public PedidoComponenteDTO(PedidoComponente obj) {
		this.quantidade = obj.getQuantidade();
		this.componente = new ComponenteDTO(obj.getComponente());
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public ComponenteDTO getComponente() {
		return componente;
	}

}
