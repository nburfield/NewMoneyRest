package com.newmoneyrest.ResourceBuilder.FundamentalData;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newmoneyrest.ResourceBuilder.NetworkRequest.Ftp;
import com.newmoneyrest.ResourceBuilder.SECFormParsers.FormParser;
import com.newmoneyrest.ResourceBuilder.error.Logger;
import com.newmoneyrest.ResourceBuilder.error.Logger.ErrorLog;
import com.newmoneyrest.ResourceBuilder.system.DateVerification;

public class IndexRetrieval {
	// Local variables
	private Map<String, List<DailyData>> fileContents;
	private String Location;
	private static final String server = "ftp.sec.gov";
	private static final String user = "anonymous";
	private static final String password = "n.burf@yahoo.com";
	
	public IndexRetrieval(Integer year, Integer quarter) {
		
		// Check year
		if(year > LocalDate.now().getYear()) {
			year = LocalDate.now().getYear();
		}
		
		// Check quarter
		if(year == LocalDate.now().getYear()) {
			if(((float)quarter / 4.0) > ((float)LocalDate.now().getMonthValue() / 12.0)) {
				quarter = (int)Math.ceil(((float)LocalDate.now().getMonthValue() / 12.0) * 4);
			}
		}
		
		Location = "edgar/full-index/" + year.toString() + "/QTR" + quarter.toString() + "/master.idx";
		
		// Logger.Log.Write("Running index Retrieval on: " + Location);
		Ftp ftpConnection = new Ftp(server, user, password);		
		StringBuilder data = ftpConnection.Request(Location);
		while(data.length() == 0) {
			ErrorLog.Write("IndexRetrieval error getting index.");
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				ErrorLog.Write("System pause: " + e.getMessage());
			}
			data = ftpConnection.Request(Location);
		}
		fileContents = IndexParser.ParseDaily(data);
	}
	
	public IndexRetrieval(LocalDate date) {
		
		LocalDate day;
		// Check the date is not in future
		if(DateVerification.DateTimeIsGreater(date, LocalDate.now().minusDays(1))) {
			day = LocalDate.now().minusDays(1);
		}
		else {
			day = date;
		}
		
		if(day.getDayOfWeek() == DayOfWeek.SATURDAY) {
			day = day.minusDays(1);
	    }
	    else if(day.getDayOfWeek() == DayOfWeek.SUNDAY) {
	    	day = day.minusDays(2);
	    }

		String [] dateArray = day.toString().split("-");
		Location = "edgar/daily-index/master." + dateArray[0] + dateArray[1] + dateArray[2] + ".idx";
		
		// Logger.Log.Write("Running index Retrieval on: " + Location);
		Ftp ftpConnection = new Ftp(server, user, password);		
		StringBuilder data = ftpConnection.Request(Location);
		while(data.length() == 0) {
			ErrorLog.Write("IndexRetrieval error getting index.");
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				ErrorLog.Write("System pause: " + e.getMessage());
			}
			data = ftpConnection.Request(Location);
		}
		fileContents = IndexParser.ParseDaily(data);
	}
	
	public IndexRetrieval() {
		fileContents = new HashMap<String, List<DailyData>>();
	}
	
	public Map<String, List<DailyData>> GetIndexList() {
		return fileContents;
	}

	public void RunForms() {
		for(String key : fileContents.keySet()) {
			FormParser driver_class;
			try {
				driver_class = (FormParser) Class.forName("SECFormParsers.Form" + key).newInstance();
				driver_class.Init(fileContents.get(key));
			} 
			catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				ErrorLog.Write("Error getting the class named: " + e.getMessage());
			}
		}
	}
	
	public void DumpMetaData(int year, int quarter) {
		File file = new File("metadata_" + year + "_" + quarter);
		Logger.WriteFile(file, "Writing data for the year: " + year + " with the quarter " + quarter);
		for(String key : fileContents.keySet()) {
			  Logger.WriteFile(file, key + "\t" + fileContents.get(key).size());
		}
	}
	
	public void DumpMetaData(LocalDate date) {
		File file = new File("metadata_" + date.toString());
		Logger.WriteFile(file, "Writing data for the day: " + date.toString());
		for(String key : fileContents.keySet()) {
			  Logger.WriteFile(file, key + "\t" + fileContents.get(key).size());
		}
	}
}
