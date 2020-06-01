package com.ifs.coeln.dto;

import java.io.Serializable;

import com.ifs.coeln.entities.Tipo;

public class TipoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;

	public TipoDTO(Tipo obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

}
