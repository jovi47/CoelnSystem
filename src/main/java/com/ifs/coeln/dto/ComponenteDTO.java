package com.ifs.coeln.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ifs.coeln.entities.Componente;
import com.ifs.coeln.entities.Item;
import com.ifs.coeln.entities.Observacao;
import com.ifs.coeln.entities.Tipo;

public class ComponenteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private Tipo tipo;
	private String descricao;
	private List<Observacao> observacoes;
	private List<Item> itens;

	public ComponenteDTO() {

	}

	public ComponenteDTO(Componente componente) {
		this.id = componente.getId();
		this.nome = componente.getNome();
		this.tipo = componente.getTipo();
		this.descricao = componente.getDescricao();
		this.observacoes = new ArrayList<>();
		observacoes.addAll(componente.getObservacoes());
		this.itens = new ArrayList<>();
		itens.addAll(componente.getItens());
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

	public List<Item> getItens() {
		return itens;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public List<Observacao> getObservacoes() {
		return observacoes;
	}

}
