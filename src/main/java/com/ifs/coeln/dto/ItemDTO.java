package com.ifs.coeln.dto;

import java.io.Serializable;

import com.ifs.coeln.entities.Item;

public class ItemDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private ComponenteDTO componente;
	private OrganizadorDTO organizador;
	private Integer quantidade;

	public ItemDTO() {

	}

	public ItemDTO(Item item) {
		this.id = item.getId();
		this.componente = new ComponenteDTO(item.getComponente());
		this.organizador = new OrganizadorDTO(item.getOrganizador());
		this.quantidade = item.getQuantidade();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ComponenteDTO getComponente() {
		return componente;
	}

	public void setComponente(ComponenteDTO componente) {
		this.componente = componente;
	}

	public OrganizadorDTO getOrganizador() {
		return organizador;
	}

	public void setOrganizador(OrganizadorDTO organizador) {
		this.organizador = organizador;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
