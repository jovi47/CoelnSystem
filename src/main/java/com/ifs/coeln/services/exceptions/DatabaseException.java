package com.ifs.coeln.services.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class DatabaseException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	
	public DatabaseException(String msg) {
		super(msg);
	}
	
	public DatabaseException(DataIntegrityViolationException e, String nomeClasse) {
		if(e.getMostSpecificCause().toString().contains("Duplicate entry")) {
			throw new DatabaseException("Um "+nomeClasse+ " ja possui esse nome");
		}
	}
	
}
