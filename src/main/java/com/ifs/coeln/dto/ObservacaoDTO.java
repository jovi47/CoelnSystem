package com.ifs.coeln.dto;

import java.io.Serializable;

import com.ifs.coeln.entities.Observacao;

public class ObservacaoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String texto;

	public ObservacaoDTO() {

	}

	public ObservacaoDTO(Observacao obj) {
		this.id = obj.getId();
		this.texto = obj.getTexto();
	}

	public Long getId() {
		return id;
	}

	public String getTexto() {
		return texto;
	}

}
