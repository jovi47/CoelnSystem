package com.ifs.coeln.dto;

import java.io.Serializable;

import com.ifs.coeln.entities.Laboratorio;

public class LaboratorioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	public LaboratorioDTO(Laboratorio obj) {
		this.id = obj.getId();
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "[id=" + id + "]";
	}
	
}
