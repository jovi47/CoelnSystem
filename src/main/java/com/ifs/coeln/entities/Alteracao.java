package com.ifs.coeln.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "alteracao")
public class Alteracao implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nome_campo;
	@Column(nullable = false)
	private String valor_antigo;
	@Column(nullable = false)
	private String valor_novo;

	public Alteracao() {

	}

	public Alteracao(Alteracao alteracao) {
		this.nome_campo = alteracao.getNome_campo();
		this.valor_antigo = alteracao.getValor_antigo();
		this.valor_novo = alteracao.getValor_novo();
	}

	public Alteracao(String nome_campo, String valor_antigo, String valor_novo) {
		this.nome_campo = nome_campo;
		this.valor_antigo = valor_antigo;
		this.valor_novo = valor_novo;
	}

	public String getNome_campo() {
		return nome_campo;
	}

	public void setNome_campo(String nome_campo) {
		this.nome_campo = nome_campo;
	}

	public String getValor_antigo() {
		return valor_antigo;
	}

	public void setValor_antigo(String valor_antigo) {
		this.valor_antigo = valor_antigo;
	}

	public String getValor_novo() {
		return valor_novo;
	}

	public void setValor_novo(String valor_novo) {
		this.valor_novo = valor_novo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Alteracao other = (Alteracao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
