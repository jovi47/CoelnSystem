package com.ifs.coeln.dto;

import java.io.Serializable;
import java.time.Instant;

import com.ifs.coeln.entities.Historico;

public class HistoricoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String insercao_delecao;
	private String id_objeto;
	private String nome_tabela;
	private Long login_id;
	private Instant data;

	public HistoricoDTO(Historico obj) {
		this.id = obj.getId();
		this.insercao_delecao = obj.getInsercao_delecao();
		this.id_objeto = obj.getId_objeto();
		this.nome_tabela = obj.getNome_tabela();
		this.login_id = obj.getLogin_id();
		this.data=obj.getData();
	}
	
	public Instant getData() {
		return data;
	}
	
	public Long getId() {
		return id;
	}

	public String getInsercao_delecao() {
		return insercao_delecao;
	}

	public String getId_objeto() {
		return id_objeto;
	}

	public String getNome_tabela() {
		return nome_tabela;
	}

	public Long getLogin_id() {
		return login_id;
	}

}
