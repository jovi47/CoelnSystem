package com.ifs.coeln.entities.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ifs.coeln.entities.Alteracao;
import com.ifs.coeln.entities.Atualizacao;

@Embeddable
public class AtualizacaoAlteracaoPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "alteracao_id")
	private Alteracao alteracao;
	@ManyToOne
	@JoinColumn(name = "atualizacao_id")
	private Atualizacao atualizacao;

	public Alteracao getAlteracao() {
		return alteracao;
	}

	public void setAlteracao(Alteracao alteracao) {
		this.alteracao = alteracao;
	}
	
	public Atualizacao getAtualizacao() {
		return atualizacao;
	}

	public void setAtualizacao(Atualizacao atualizacao) {
		this.atualizacao = atualizacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alteracao == null) ? 0 : alteracao.hashCode());
		result = prime * result + ((atualizacao == null) ? 0 : atualizacao.hashCode());
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
		AtualizacaoAlteracaoPK other = (AtualizacaoAlteracaoPK) obj;
		if (alteracao == null) {
			if (other.alteracao != null)
				return false;
		} else if (!alteracao.equals(other.alteracao))
			return false;
		if (atualizacao == null) {
			if (other.atualizacao != null)
				return false;
		} else if (!atualizacao.equals(other.atualizacao))
			return false;
		return true;
	}

}
