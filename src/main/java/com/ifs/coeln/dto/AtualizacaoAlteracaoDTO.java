package com.ifs.coeln.dto;

import java.io.Serializable;

import com.ifs.coeln.entities.Alteracao;
import com.ifs.coeln.entities.AtualizacaoAlteracao;

public class AtualizacaoAlteracaoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Alteracao alteracao;

	public AtualizacaoAlteracaoDTO(AtualizacaoAlteracao obj) {
		this.alteracao = new Alteracao(obj.getAlteracao());
	}
	
	
	public Alteracao getAlteracao() {
		return alteracao;
	}
}
