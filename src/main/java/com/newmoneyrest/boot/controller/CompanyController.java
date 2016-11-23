package com.newmoneyrest.boot.controller;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.newmoneyrest.ResourceBuilder.MarketData.Technical;
import com.newmoneyrest.boot.model.Company;
import com.newmoneyrest.boot.model.HistoricalDatum;
import com.newmoneyrest.boot.repository.CompanyRepository;

@RestController
public class CompanyController {

	@Autowired
	private CompanyRepository companyRepository;
	
	@GetMapping(value = "/setup")
	public void setup() {
		Company c = new Company("0001326801", "Facebook Inc.", "FB", null, null, null, null, null, null, null);
		companyRepository.save(c);
		
		Technical techincal = new Technical(companyRepository);
		techincal.pullHistoricalData(c.getTicker());
	}
	
	@GetMapping(value = "/company")
	public @ResponseBody ArrayList<Company> getCompanies() {
		return companyRepository.findAll();
	}
	
	@GetMapping(value = "/company/{ticker}")
	public @ResponseBody Company getCompany(@PathVariable("ticker") String stockId) {
		return companyRepository.findByTicker(stockId);
	}
	
	@GetMapping(value = "/historicaldata/{ticker}")
	public @ResponseBody Set<HistoricalDatum> getCompanyData(@PathVariable("ticker") String stockId) {
		return companyRepository.findByTicker(stockId).getHistoricalData();
	}
}
