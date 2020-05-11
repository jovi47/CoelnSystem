package com.ifs.coeln.dto;

import java.io.Serializable;

import com.ifs.coeln.entities.Organizador;

public class OrganizadorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private LaboratorioDTO laboratorio;

	public OrganizadorDTO() {

	}

	public OrganizadorDTO(Organizador organizador) {
		this.id = organizador.getId();
		this.laboratorio = new LaboratorioDTO(organizador.getLaboratorio());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LaboratorioDTO getLaboratorio() {
		return laboratorio;
	}

}
