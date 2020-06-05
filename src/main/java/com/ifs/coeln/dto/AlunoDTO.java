package com.ifs.coeln.dto;

import java.io.Serializable;

import com.ifs.coeln.entities.Usuario;

public class AlunoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private TurmaDTO turma;
	private String nome_projeto;
	private String matricula;
	private CursoDTO curso;
	private String nome;

	public AlunoDTO(Usuario aluno) {
		this.nome = aluno.getNome();
		this.turma = new TurmaDTO(aluno.getTurma());
		this.nome_projeto = aluno.getNome_projeto();
		this.curso = new CursoDTO(aluno.getCurso());
		this.matricula = aluno.getMatricula();
	}

	public TurmaDTO getTurma() {
		return turma;
	}

	public String getNome_projeto() {
		return nome_projeto;
	}

	public String getMatricula() {
		return matricula;
	}

	public CursoDTO getCurso() {
		return curso;
	}

	public String getNome() {
		return nome;
	}

}
