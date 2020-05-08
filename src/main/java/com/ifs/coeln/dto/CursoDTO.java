package com.ifs.coeln.dto;

import java.io.Serializable;

import com.ifs.coeln.entities.Curso;

public class CursoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;

	public CursoDTO() {

	}
	
	public CursoDTO(Curso curso) {
		this.id = curso.getId();
		this.nome = curso.getNome();
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
