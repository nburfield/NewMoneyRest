package com.newmoneyrest.boot.repository;

import org.springframework.data.repository.CrudRepository;

import com.newmoneyrest.boot.model.HistoricalDatum;

public interface HistoricalDatumRepository extends CrudRepository<HistoricalDatum, Long> {
	
}
