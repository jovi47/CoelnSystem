package com.ifs.coeln.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ifs.coeln.entities.Componente;
import com.ifs.coeln.entities.Tipo;

public class TipoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private List<Componente> componentes;

	public TipoDTO() {

	}

	public TipoDTO(Tipo tipo) {
		this.id = tipo.getId();
		this.nome = tipo.getNome();
		this.componentes = new ArrayList<>();
		componentes.addAll(tipo.getComponentes());
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

	public List<Componente> getComponentes() {
		return componentes;
	}
	
	
}
