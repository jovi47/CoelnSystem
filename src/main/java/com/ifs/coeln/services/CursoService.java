package com.ifs.coeln.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifs.coeln.repositories.CursoRepository;

@Service
public class CursoService {
	
	@Autowired
	CursoRepository repository;
}
