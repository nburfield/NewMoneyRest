package com.newmoneyrest.boot.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.newmoneyrest.boot.model.InsiderForm4;

public interface InsiderForm4Repository extends CrudRepository<InsiderForm4, Long> {
	public List<InsiderForm4> findAllByHeaderForm4Id(String headerForm4Id);
}
