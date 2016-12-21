package com.newmoneyrest.boot.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.newmoneyrest.boot.model.Nonderivative;

public interface NonderivativeRepository extends CrudRepository<Nonderivative, Long> {
	public Set<Nonderivative> findAllByHeaderForm4Id(String headerForm4Id);
}
