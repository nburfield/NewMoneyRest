package com.newmoneyrest.boot.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.newmoneyrest.boot.model.Insider;

public interface InsiderRepository extends CrudRepository<Insider, Long> {
	public Insider findByCik(String cik);
	public ArrayList<Insider> findAll();
}
