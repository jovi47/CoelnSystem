package com.ifs.coeln.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ifs.coeln.entities.Componente;

public class ComponenteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private TipoDTO tipo;
	private String descricao;
	private List<ObservacaoDTO> observacoes = new ArrayList<>();

	public ComponenteDTO(Componente obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.tipo = new TipoDTO(obj.getTipo());
		this.descricao = obj.getDescricao();
		obj.getObservacoes().forEach(obs -> observacoes.add(new ObservacaoDTO(obs)));
	}

	public TipoDTO getTipo() {
		return tipo;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public List<ObservacaoDTO> getObservacoes() {
		return observacoes;
	}

}
