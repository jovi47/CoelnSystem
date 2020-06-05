package com.ifs.coeln.dto;

import java.io.Serializable;

import com.ifs.coeln.entities.Usuario;

public class ServidorDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;
	private CursoDTO curso;
	private String matricula;

	public ServidorDTO(Usuario servidor) {
		this.nome = servidor.getNome();
		this.matricula = servidor.getMatricula();
		this.curso = new CursoDTO(servidor.getCurso());
	}

	public String getNome() {
		return nome;
	}

	public CursoDTO getCurso() {
		return curso;
	}

	public String getMatricula() {
		return matricula;
	}

}
