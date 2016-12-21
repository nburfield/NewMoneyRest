package com.newmoneyrest.ResourceBuilder.FundamentalData;

public class DailyData {
	public String CIK;
	public String companyName; 
	public String formType;
	public String dateFiled; 
	public String fileName;
	
	public DailyData(String cik, String companyname, String formtype, String datefiled, String filename) {
		CIK = cik;
		companyName = companyname; 
		formType = formtype;
		dateFiled = datefiled; 
		fileName = filename;
	}
	public DailyData(String [] data) {
		CIK = data[0];
		companyName = data[1]; 
		formType = data[2];
		dateFiled = data[3]; 
		fileName = data[4];
	}
}
