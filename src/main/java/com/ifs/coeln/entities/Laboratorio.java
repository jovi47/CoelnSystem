package com.ifs.coeln.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "laboratorio")
public class Laboratorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(columnDefinition = "boolean default false")
	private Boolean is_deleted;
	@JsonIgnore
	@OneToMany(mappedBy = "laboratorio")
	private List<Organizador> organizadores = new ArrayList<>();

	public Laboratorio() {

	}

	public Laboratorio(Laboratorio obj) {
		this.id = obj.getId();
		this.is_deleted = (obj.getIs_deleted() == null) ? false : false;
	}

	public List<Organizador> getOrganizadores() {
		return organizadores;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(Boolean is_deleted) {
		this.is_deleted = is_deleted;
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
		Laboratorio other = (Laboratorio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
