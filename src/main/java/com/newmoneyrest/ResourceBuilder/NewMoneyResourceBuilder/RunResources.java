package com.newmoneyrest.ResourceBuilder.NewMoneyResourceBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.newmoneyrest.boot.model.Company;
import com.newmoneyrest.boot.repository.CompanyRepository;

@Controller
public class RunResources {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	public RunResources() {}
	
	@GetMapping(value = "/test")
    public void addTable() {
    	System.out.println("This should work");
    	Company c = new Company("CIKTest", "NameYo", "Ticker", null, null, null, null, null, null, null);
		companyRepository.save(c);
    }
}
