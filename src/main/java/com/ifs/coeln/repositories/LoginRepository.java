package com.ifs.coeln.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifs.coeln.entities.Login;

public interface LoginRepository extends JpaRepository<Login, Long>{

}
