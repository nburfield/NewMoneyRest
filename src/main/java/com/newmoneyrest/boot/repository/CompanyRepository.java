package com.newmoneyrest.boot.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.newmoneyrest.boot.model.Company;

public interface CompanyRepository extends CrudRepository<Company, Long> {
	public Company findByName(String name);
	public Company findByTicker(String ticker);
	public ArrayList<Company> findAll();
}
