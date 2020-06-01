package com.ifs.coeln.dto;

import java.io.Serializable;

import com.ifs.coeln.entities.Item;

public class ItemDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private ComponenteDTO componente;
	private OrganizadorDTO organizador;
	private Integer quantidade;

	public ItemDTO(Item obj) {
		this.id = obj.getId();
		this.componente = new ComponenteDTO(obj.getComponente());
		this.organizador = new OrganizadorDTO(obj.getOrganizador());
		this.quantidade = obj.getQuantidade();
	}

	public Long getId() {
		return id;
	}

	public ComponenteDTO getComponente() {
		return componente;
	}

	public OrganizadorDTO getOrganizador() {
		return organizador;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

}
