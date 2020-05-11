package com.ifs.coeln.dto;

import java.io.Serializable;

import com.ifs.coeln.entities.Laboratorio;

public class LaboratorioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;

	public LaboratorioDTO() {

	}

	public LaboratorioDTO(Laboratorio lab) {
		this.id = lab.getId();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
