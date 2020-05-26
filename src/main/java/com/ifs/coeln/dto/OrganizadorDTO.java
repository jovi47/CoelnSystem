package com.ifs.coeln.dto;

import java.io.Serializable;

import com.ifs.coeln.entities.Organizador;

public class OrganizadorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private LaboratorioDTO laboratorio;

	public OrganizadorDTO() {

	}

	public OrganizadorDTO(Organizador obj) {
		this.id = obj.getId();
		this.laboratorio = new LaboratorioDTO(obj.getLaboratorio());
	}

	public Long getId() {
		return id;
	}

	public LaboratorioDTO getLaboratorio() {
		return laboratorio;
	}

}
