package com.ifs.coeln.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "componente")
public class Componente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String nome;

	@Column(columnDefinition = "varchar(255) default '--'")
	private String descricao;

	@Column(columnDefinition = "boolean default false")
	private Boolean is_deleted;

	@ManyToOne
	@JoinColumn(name = "tipo_id", nullable = false)
	private Tipo tipo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "componente")
	private List<Observacao> observacoes = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "componente")
	private List<Item> itens = new ArrayList<>();

	public Componente() {

	}

	public Componente(Componente componente) {
		this.id = componente.getId();
		this.nome = componente.getNome();
		this.descricao = (componente.getDescricao() == null) ? "--" : componente.getDescricao();
		this.is_deleted = (componente.getIs_deleted() == null) ? false : false;
		this.tipo = componente.getTipo();
	}

	public List<Item> getItens() {
		return itens;
	}

	public List<Observacao> getObservacoes() {
		return observacoes;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(Boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
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
		Componente other = (Componente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
