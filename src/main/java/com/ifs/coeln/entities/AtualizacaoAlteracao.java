package com.ifs.coeln.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ifs.coeln.entities.pk.AtualizacaoAlteracaoPK;

@Entity
@Table(name = "atualizacao_alteracao")
public class AtualizacaoAlteracao {
	@EmbeddedId
	private AtualizacaoAlteracaoPK id = new AtualizacaoAlteracaoPK();

	public AtualizacaoAlteracao() {

	}

	public AtualizacaoAlteracao(Atualizacao atualizacao, Alteracao alteracao) {
		id.setAlteracao(alteracao);
		id.setAtualizacao(atualizacao);
	}	
	
	public void setAtualizacao(Atualizacao atualizacao) {
		id.setAtualizacao(atualizacao);
	}
	
	public void setAlteracao(Alteracao alteracao) {
		id.setAlteracao(alteracao);
	}
	
	public Atualizacao getAtualizacao() {
		return id.getAtualizacao();
	}
	
	public Alteracao getAlteracao() {
		return id.getAlteracao();
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
		AtualizacaoAlteracao other = (AtualizacaoAlteracao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
