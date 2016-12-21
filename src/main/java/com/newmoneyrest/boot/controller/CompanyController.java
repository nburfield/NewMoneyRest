package com.newmoneyrest.boot.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.newmoneyrest.ResourceBuilder.FundamentalData.DailyData;
import com.newmoneyrest.ResourceBuilder.MarketData.Technical;
import com.newmoneyrest.ResourceBuilder.SECFormParsers.Form4;
import com.newmoneyrest.boot.model.Company;
import com.newmoneyrest.boot.model.Derivative;
import com.newmoneyrest.boot.model.HistoricalDatum;
import com.newmoneyrest.boot.model.Insider;
import com.newmoneyrest.boot.model.InsiderForm4;
import com.newmoneyrest.boot.model.Nonderivative;
import com.newmoneyrest.boot.repository.CompanyRepository;
import com.newmoneyrest.boot.repository.DerivativeRepository;
import com.newmoneyrest.boot.repository.InsiderForm4Repository;
import com.newmoneyrest.boot.repository.InsiderRepository;
import com.newmoneyrest.boot.repository.NonderivativeRepository;

@RestController
public class CompanyController {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private InsiderRepository insiderRepository;
	
	@Autowired
	private DerivativeRepository derivativeRepository;
	
	@Autowired
	private NonderivativeRepository nonderivativeRepository;
	
	@Autowired
	InsiderForm4Repository insiderForm4Repository;
	
	@GetMapping(value = "/setup")
	public void setup() {
		// Company c = new Company("0001067983", "Berkshire Hathaway Inc", "BRK-A", "NYSE", "1440 KIEWIT PLZ", "STE 1440", "OMAHA", "NE", "68131", null);
		Company c = new Company("0001326801", "Facebook Inc", "FB", "NASDAQ", "1601 WILLOW ROAD", null, "MENLO PARK", "CA", "94025", null);
		//Company c = new Company("0001059556", "MOODYS CORP DE", "MCO", "NYSE", "7 WORLD TRADE CENTER", "AT 250 GREENWICH STREET", "New York", "NY", "10007", null);
		companyRepository.save(c);
		
		Technical techincal = new Technical(companyRepository);
		techincal.pullHistoricalData(c.getTicker());
		
		Form4 form4 = new Form4(companyRepository, insiderRepository, nonderivativeRepository, derivativeRepository);
		List<DailyData> dd = new ArrayList<DailyData>();
		DailyData d = new DailyData("0001059556", "MOODYS CORP /DE/", "4", "2009-10-30", "edgar/data/1059556/0001181431-09-049353.txt");
		//DailyData d = new DailyData("0001067983", "BERKSHIRE HATHAWAY INC", "4", "2006-09-29", "edgar/data/1067983/0001181431-06-055590.txt");
		dd.add(d);
		//form4.Init(dd);
	}
	
	@GetMapping(value = "/company")
	public @ResponseBody ArrayList<Company> getCompanies() {
		return companyRepository.findAll();
	}
	
	@GetMapping(value = "/company/{ticker}")
	public @ResponseBody Company getCompany(@PathVariable("ticker") String stockId) {
		return companyRepository.findByTicker(stockId);
	}
	
	@GetMapping(value = "/company/{ticker}/insider")
	public @ResponseBody Set<InsiderForm4> getCompanyInsider(@PathVariable("ticker") String stockId) {
		return companyRepository.findByTicker(stockId).getInsiderForm4();
	}
	
	@GetMapping(value = "/company/{ticker}/insiders")
	public @ResponseBody Set<Insider> getCompanyInsiders(@PathVariable("ticker") String stockId) {
		Set<Insider> insiders = new HashSet<Insider>();
		for(InsiderForm4 insiderForm : companyRepository.findByTicker(stockId).getInsiderForm4()) {
			insiders.add(insiderForm.getInsider());
		}
		return insiders;
	}
	
	@GetMapping(value = "/insider")
	public @ResponseBody ArrayList<Insider> getInsiders() {
		return insiderRepository.findAll();
	}
	
	@GetMapping(value = "/insider/{cik}")
	public @ResponseBody Insider getInsider(@PathVariable("cik") String insiderId) {
		return insiderRepository.findByCik(insiderId);
	}
	
	@GetMapping(value = "/insider/{cik}/trades")
	public @ResponseBody Set<InsiderForm4> getInsiderTrades(@PathVariable("cik") String insiderId) {
		return insiderRepository.findByCik(insiderId).getForm4();
	}
	
	@GetMapping(value = "/insider/company/{headerForm4}")
	public @ResponseBody Company getCompanyOnTrade(@PathVariable("headerForm4") String headerFrom4) {
		return insiderForm4Repository.findAllByHeaderForm4Id(headerFrom4).get(0).getCompany();
	}
	
	@GetMapping(value = "/insiders/nonderiviative/{headerForm4}")
	public @ResponseBody Set<Nonderivative> getCompanyInsidersNonderiviativeTranscation(@PathVariable("headerForm4") String headerFrom4) {
		return nonderivativeRepository.findAllByHeaderForm4Id(headerFrom4);
	}
	
	@GetMapping(value = "/insiders/deriviative/{headerForm4}")
	public @ResponseBody Set<Derivative> getCompanyInsidersDeriviativeTranscation(@PathVariable("headerForm4") String headerFrom4) {
		return derivativeRepository.findAllByHeaderForm4Id(headerFrom4);
	}
	
	@GetMapping(value = "/historicaldata/{ticker}")
	public @ResponseBody Set<HistoricalDatum> getCompanyData(@PathVariable("ticker") String stockId) {
		return companyRepository.findByTicker(stockId).getHistoricalData();
	}
}
