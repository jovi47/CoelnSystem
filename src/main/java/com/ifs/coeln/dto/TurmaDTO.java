package com.ifs.coeln.dto;

import java.io.Serializable;

import com.ifs.coeln.entities.Turma;

public class TurmaDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;

	public TurmaDTO() {

	}

	public TurmaDTO(Turma turma) {
		this.id = turma.getId();
		this.nome = turma.getNome();
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
