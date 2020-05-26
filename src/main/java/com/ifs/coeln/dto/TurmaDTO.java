package com.ifs.coeln.dto;

import java.io.Serializable;

import com.ifs.coeln.entities.Turma;

public class TurmaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;

	public TurmaDTO() {

	}

	public TurmaDTO(Turma obj) {
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
