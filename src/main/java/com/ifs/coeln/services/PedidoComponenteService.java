package com.ifs.coeln.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifs.coeln.repositories.PedidoComponenteRepository;

@Service
public class PedidoComponenteService {

	@Autowired
	PedidoComponenteRepository repository;
}
