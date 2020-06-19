package com.ifs.coeln.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ifs.coeln.entities.pk.PedidoComponentePK;

@Entity
@Table(name = "pedido_componente")
public class PedidoComponente implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PedidoComponentePK id = new PedidoComponentePK();
	
	@Column(nullable = false)
	private Integer quantidade;

	public PedidoComponente() {

	}

	public PedidoComponente(Pedido pedido, Componente componente, Integer quantidade) {
		id.setPedido(pedido);
		id.setComponente(componente);
		this.quantidade = quantidade;
	}

	public void setPedido(Pedido pedido) {
		id.setPedido(pedido);
	}
	@JsonIgnore
	public Pedido getPedido() {
		return id.getPedido();
	}

	public void setComponente(Componente componente) {
		id.setComponente(componente);
	}

	public Componente getComponente() {
		return id.getComponente();
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
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
		PedidoComponente other = (PedidoComponente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PedidoComponente [id=" + id + ", quantidade=" + quantidade + "]";
	}
	
	
}
