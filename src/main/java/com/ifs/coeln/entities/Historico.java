package com.ifs.coeln.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "historico")
public class Historico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String insercao_delecao;
	
	@Column(nullable = false)
	private String id_objeto;
	
	@Column(nullable = false)
	private String nome_tabela;
	
	@Column(nullable = false)
	private Long login_id;
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
	private Instant data;

	public Historico() {

	}

	public Historico(Long id, String insercao_delecao, String id_objeto, String nome_tabela, Long login_id,
			Instant data) {
		super();
		this.id = id;
		this.insercao_delecao = insercao_delecao;
		this.id_objeto = id_objeto;
		this.nome_tabela = nome_tabela;
		this.login_id = login_id;
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInsercao_delecao() {
		return insercao_delecao;
	}

	public void setInsercao_delecao(String insercao_delecao) {
		this.insercao_delecao = insercao_delecao;
	}

	public String getId_objeto() {
		return id_objeto;
	}

	public void setId_objeto(String id_objeto) {
		this.id_objeto = id_objeto;
	}

	public String getNome_tabela() {
		return nome_tabela;
	}

	public void setNome_tabela(String nome_tabela) {
		this.nome_tabela = nome_tabela;
	}

	public Long getLogin_id() {
		return login_id;
	}

	public void setLogin_id(Long login_id) {
		this.login_id = login_id;
	}

	public Instant getData() {
		return data;
	}

	public void setData(Instant data) {
		this.data = data;
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
		Historico other = (Historico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
