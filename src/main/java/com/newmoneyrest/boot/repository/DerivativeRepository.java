package com.newmoneyrest.boot.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.newmoneyrest.boot.model.Derivative;

public interface DerivativeRepository extends CrudRepository<Derivative, Long> {
	public Set<Derivative> findAllByHeaderForm4Id(String headerForm4Id);
}
