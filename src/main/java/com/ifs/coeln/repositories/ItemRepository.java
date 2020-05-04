package com.ifs.coeln.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifs.coeln.entities.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
