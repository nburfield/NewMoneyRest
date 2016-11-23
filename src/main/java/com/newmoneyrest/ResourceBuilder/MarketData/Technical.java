package com.newmoneyrest.ResourceBuilder.MarketData;

import java.util.HashSet;

import com.newmoneyrest.ResourceBuilder.error.Logger;
import com.newmoneyrest.boot.model.Company;
import com.newmoneyrest.boot.model.HistoricalDatum;
import com.newmoneyrest.boot.repository.CompanyRepository;

public class Technical {

	private CompanyRepository companyRepository;
	
	/**
	 * Constructor of the Technical class to check
	 * if the database is built with all tables.
	 */
	public Technical(CompanyRepository cr) {
		companyRepository = cr;
	}
	
	/**
	 * Gets the Historical data of all the tickers in the string array.
	 * @param stockTickers The string array of the tickers to pull.
	 */
	public void pullHistoricalData(String [] stockTickers) {
		for (String key : stockTickers){
			pullHistoricalData(key);
		}
	}
	
	/**
	 * Gets the Historical data of the stock passed in.
	 * @param stock The ticker to pull the data of.
	 */
	public boolean pullHistoricalData(String stock) {
		StringBuffer temp_container;
		try {
			temp_container = RequestStockInfo.RequestCSV.GetHistoricalInformation(stock);
			insertIntoDataBase(stock, temp_container.toString().replace("-", ",").split("\\n"));
			return true;
		}
		catch(Exception e) {
			Logger.ErrorLog.Write("Error getting the data to the database: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Inserts the data pulled for the given stock into the database.
	 * @param stockName The ticker of the stock pulled.
	 * @param container The String Array that has the comma separated data for each day.
	 */
	private void insertIntoDataBase(String stockName, String [] container) {
		Company company = companyRepository.findByTicker(stockName);
		HashSet<HistoricalDatum> hdSet = new HashSet<HistoricalDatum>();

		// Iterates through the array of strings and adds the line to the database
		for (int index = 1; index < container.length; index++) {
			try {
				String [] splitContainer = container[index].split(",");
				HistoricalDatum hd = new HistoricalDatum(company, Integer.parseInt(splitContainer[0]), Integer.parseInt(splitContainer[1]), Integer.parseInt(splitContainer[2]), Double.parseDouble(splitContainer[3]), Double.parseDouble(splitContainer[4]), Double.parseDouble(splitContainer[5]), Double.parseDouble(splitContainer[6]), Double.parseDouble(splitContainer[7]), Double.parseDouble(splitContainer[8]));
				hdSet.add(hd);
			}
			catch(Exception e) {
				Logger.ErrorLog.Write("Error with parsing the historical data: " + e.getMessage());
			}
		}
		
		try {
			company.setHistoricalData(hdSet);
			companyRepository.save(company);
		}
		catch(Exception e) {
			Logger.ErrorLog.Write("Error with Inserting into Database: " + e.getMessage());
		}

	}
}
