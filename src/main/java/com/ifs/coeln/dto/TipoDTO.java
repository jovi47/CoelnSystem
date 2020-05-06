package com.ifs.coeln.dto;

import java.io.Serializable;

import com.ifs.coeln.entities.Tipo;

public class TipoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	
	public TipoDTO() {

	}

	public TipoDTO(Tipo tipo) {
		this.id = tipo.getId();
		this.nome = tipo.getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
}
