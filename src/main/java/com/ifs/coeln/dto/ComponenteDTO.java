package com.ifs.coeln.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ifs.coeln.entities.Componente;
import com.ifs.coeln.entities.Observacao;

public class ComponenteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private TipoDTO tipo;
	private String descricao;
	private List<Observacao> observacoes = new ArrayList<>();

	public ComponenteDTO() {

	}

	public ComponenteDTO(Componente componente) {
		this.id = componente.getId();
		this.nome = componente.getNome();
		this.tipo = new TipoDTO(componente.getTipo());
		this.descricao = componente.getDescricao();
		observacoes.addAll(componente.getObservacoes());
	}

	public TipoDTO getTipo() {
		return tipo;
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

	public List<Observacao> getObservacoes() {
		return observacoes;
	}

}
