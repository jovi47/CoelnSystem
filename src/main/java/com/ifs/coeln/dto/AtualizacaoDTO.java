package com.ifs.coeln.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.ifs.coeln.entities.Atualizacao;

public class AtualizacaoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long login_id;
	private String id_objeto;
	private String nome_tabela;
	private Instant data;
	private Set<AtualizacaoAlteracaoDTO> mudancas = new HashSet<>();

	public AtualizacaoDTO(Atualizacao atualizacao) {
		this.id = atualizacao.getId();
		this.id_objeto = atualizacao.getId_objeto();
		this.login_id = atualizacao.getLogin_id();
		this.nome_tabela = atualizacao.getNome_tabela();
		this.data = atualizacao.getData();
		atualizacao.getMudancas().forEach(x -> mudancas.add(new AtualizacaoAlteracaoDTO(x)));
	}

	public Instant getData() {
		return data;
	}

	public Long getId() {
		return id;
	}

	public Long getLogin_id() {
		return login_id;
	}

	public String getId_objeto() {
		return id_objeto;
	}

	public String getNome_tabela() {
		return nome_tabela;
	}

	public Set<AtualizacaoAlteracaoDTO> getMudancas() {
		return mudancas;
	}

}
