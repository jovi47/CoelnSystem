package com.ifs.coeln.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifs.coeln.repositories.OrganizadorRepository;

@Service
public class OrganizadorService {
	
	@Autowired
	OrganizadorRepository repository;
}
