package com.ifs.coeln.dto;

import java.io.Serializable;

import com.ifs.coeln.entities.Curso;

public class CursoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;

	public CursoDTO(Curso obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return "[id=" + id + "]";
	}

	
}
