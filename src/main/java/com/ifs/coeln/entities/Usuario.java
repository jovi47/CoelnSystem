package com.ifs.coeln.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String matricula;

	@ManyToOne
	@JoinColumn(name = "curso_id")
	private Curso curso;

	@ManyToOne
	@JoinColumn(name = "turma_id", nullable = true)
	private Turma turma;

	@Column(nullable = true)
	private String nome_projeto;
	
	@Column(columnDefinition = "boolean default false")
	private Boolean is_servidor;
	
	@Column(columnDefinition = "boolean default false")
	private Boolean is_deleted;
	
	private String nome;

	public Usuario() {

	}

	public Usuario(Usuario usuario) {
		super();
		this.matricula =usuario.getMatricula();
		this.nome = usuario.getNome();
		this.curso = usuario.getCurso();
		this.turma = usuario.getTurma();
		this.nome_projeto = usuario.getNome_projeto();
		this.is_servidor = usuario.getIs_servidor();
		this.is_deleted =(usuario.getIs_deleted() == null) ? false : false;;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public String getNome_projeto() {
		return nome_projeto;
	}

	public void setNome_projeto(String nome_projeto) {
		this.nome_projeto = nome_projeto;
	}

	public Boolean getIs_servidor() {
		return is_servidor;
	}

	public void setIs_servidor(Boolean is_servidor) {
		this.is_servidor = is_servidor;
	}

	public Boolean getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(Boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[matricula=" + matricula + "]";
	}

	
	
}
