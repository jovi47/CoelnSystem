package com.ifs.coeln.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String nomeClasse,Object id) {
		super(nomeClasse+", id : "+ id);
	}
}
