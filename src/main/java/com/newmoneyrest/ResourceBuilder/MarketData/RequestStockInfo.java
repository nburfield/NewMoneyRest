package com.newmoneyrest.ResourceBuilder.MarketData;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.newmoneyrest.ResourceBuilder.NetworkRequest.Http;
import com.newmoneyrest.ResourceBuilder.error.CustomExceptions;
import com.newmoneyrest.ResourceBuilder.error.Logger;

public class RequestStockInfo {
	  //private TechnicalExceptions ex = new TechnicalExceptions();
	  private String yearJSONHistorical = "http://query.yahooapis.com/v1/public/yql?q=select+*+from+yahoo.finance.historicaldata+where+symbol=%22aapl%22+and+startDate=%222013-01-01%22+and+endDate=%222013-12-31%22&format=json&env=store://datatables.org/alltableswithkeys";
	  private String yaerCSVHistorical = "http://ichart.finance.yahoo.com/table.csv?s=AAPL&c=2013&a=00&b=01&c=2013&d=11&e=31&f=2013&g=d";
	  private String yearCSVHistorical = "http://ichart.finance.yahoo.com/table.csv?s=AAPL&c=2013&a=00&b=01&c=2013&d=11&e=31&f=2013&g=d";
	  private static LocalDate startDate, endDate;
	  private static StringBuilder ticker;
	  private StringBuilder csv_url = new StringBuilder("http://ichart.finance.yahoo.com/table.csv?");
	  public StringBuilder data;
	  private static StringBuilder url;
	  private static final String RequestStockInfoLog = "RequestStockInfoLog.txt";
	  public static class RequestJSON {
	    // Variables
	    static String base_url = "http://query.yahooapis.com/v1/public/yql?q=select+*+from+yahoo.finance.historicaldata+where+";
	    static String end_url = "format=json&env=store://datatables.org/alltableswithkeys";

	    // Gets the data from the database
	    public static StringBuilder GetData(String start, String end, String t) throws CustomExceptions.InputException {
	      // List for holding problem urls
	      List<String> badURL = new ArrayList<String>();

	      // Setup the dates
	      try {
	        SetDate(start, end);
	        CheckDate();
	      }
	      catch(CustomExceptions.InputException a) {
	        throw new CustomExceptions.InputException(a.getMessage());
	      }

	      // Set the ticker
	      SetTicker(t);

	      // Call the URL
	      StringBuilder string = new StringBuilder("");

	      LocalDate bottom = LocalDate.of(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth());
	      LocalDate top = LocalDate.of(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth());
	      
	      top = top.plusYears(1);
	      while(DateTimeIsLess(top, endDate)) {
	        try {
	          string.append(Http.GetURLData(BuildURL(bottom, top)));
	        }
	        catch(CustomExceptions.NetworkError a) {
	          badURL.add(BuildURL(bottom, top));
	        }

	        // Increment
	        top = top.plusYears(1);
	        bottom = bottom.plusYears(1);
	      }

	      try {
	        string.append(Http.GetURLData(BuildURL(bottom, top)));
	      }
	      catch(CustomExceptions.NetworkError a) {
	        badURL.add(BuildURL(bottom, top));
	      }

	      // Print URLS
	      for(int i = 0; i < badURL.size(); i++) {
	        try {
	          string.append(Http.GetURLData(badURL.get(i)));
	        }
	        catch(CustomExceptions.NetworkError a) {
	          Logger.ErrorLog.Write("Could not get the url: " + badURL.get(i));
	        }
	      }

	      return string;
	    }

	    private static String BuildURL(LocalDate s, LocalDate e) {
	      StringBuilder url = new StringBuilder("");

	      // Add init
	      url.append(base_url);

	      // Add in ticker
	      url.append("symbol=%22");
	      url.append(ticker);
	      url.append("%22+and+");

	      // Add in start date
	      url.append("startDate=%22");
	      url.append(s.toString());
	      url.append("%22+and+");

	      // Add end date
	      url.append("endDate=%22");
	      url.append(e.toString());
	      url.append("%22&");

	      // Add in the end part
	      url.append(end_url);

	      return url.toString();
	    }

	  }
	  /**
	   * RequestCSV is a static inner class that returns a stock's
	   * current trading information, historical data and current technical ratios.
	   * Since the class is static it does not need to be initialized when it is used.
	   * @author Raja Singh and Nolan Burfield
	   * @version 1.0
	   * @since 11/10/15
	   * 
	   */
	  public static class RequestCSV {
	    /**
	     * This method returns all the recorded historical data for a stock  
	     * @param stockTicker is the ticker of the stock for which the historical data is being requested
	     * @return retData is a string buffer that contains the historical trading information 
	     */ 	  
	    public static StringBuffer GetHistoricalInformation(String stockTicker){
	    	Logger.Log.Write("Pulling historical data for: " + stockTicker);
	    	String yahooBaseURL = "http://real-chart.finance.yahoo.com/table.csv?s=";
	        String URLRequest = "";
	    	StringBuffer rawTickerData = new StringBuffer();
	        StringBuffer retData = new StringBuffer();
	        URLRequest += yahooBaseURL + stockTicker+"&ignore=.csv";
	        retData.append(Http.pullData(URLRequest, rawTickerData));
	        return retData;
	    }
	    /**
	     * This method returns the stock's historical data based on the date range specified 
	     * @param start_year is the year that the user wants as the start of historical data   
	     * @param start_month is the month that the user wants as the start of historical data 
	     * @param start_day is the day  that the user wants as the start of historical data 
	     * @param end_year is the year that the user wants as the end of historical data 
	     * @param end_month is the month that the user wants as the end of historical data 
	     * @param end_day is the day  that the user wants as the end of historical data 
	     * @param stockTicker is the ticker of the stock for which the historical data is being requested
	     * @return retData is a string buffer that contains the historical trading information 
	     */  
	    public static StringBuffer GetHistoricalInformation(String start_year, String start_month, String start_day, 
	            String end_year, String end_month, String end_day, String stockTicker){
	        	Logger.Log.Write("Pulling historical data for: " + stockTicker);
	        	String yahooBaseURL = "http://real-chart.finance.yahoo.com/table.csv?s=";
	        	String URLRequest = "";
	        	String firstHalf = "";
	        	StringBuffer rawTickerData = new StringBuffer();
	        	StringBuffer retData = new StringBuffer();
	    		firstHalf +=stockTicker+"&d="+Http.convertMonth(end_month)+"&e="+end_day+"&f="+end_year+"&g=d&a=";
	    		String secondHalf = "";
	    		secondHalf += Http.convertMonth(start_month)+"&b="+start_day+"&c="+start_year+"&ignore=.csv";
	    		URLRequest += yahooBaseURL + firstHalf + secondHalf;
	    		retData.append(Http.pullData(URLRequest, rawTickerData));
	    		return retData;
	        }
	    /**
	     * This method returns the stock's current trading information, it returns
	     * Name,Close,Date,Time,Gain,Open,High,Low,Volume information
	     * @param ticker is the ticker symbol for a stock
	     * @return retData is a string buffer that contains the current trading information 
	     */   
	        public static StringBuffer currentInformation(String ticker, String name){
	        	Logger.Log.Write("Pulling historical data for: " + ticker);
	        	String URLRequest = "http://download.finance.yahoo.com/d/quotes.csv?s="+ticker+"&f=sl1d1t1c1ohgv&e=.csv";
	        	StringBuffer rawTickerData = new StringBuffer();
	        	StringBuffer retData = new StringBuffer();
	        	//retData.append("Name,Ticker,Close,Gain,Open,High,Low,Volume \n");
	        	// name close date time gain open high low volumen
	        	retData.append(name);
	        	retData.append(",");
	        	retData.append(ticker);
	        	retData.append(",");        	
	        	String temp = (Http.pullData(URLRequest,rawTickerData)).toString();
	        	String [] temp2 = temp.split(",");
	        	for (int count = 0; count < temp2.length;count++){
	        		if(count!= 0 && count!=2 && count!=3){
	        			retData.append((temp2[count].toString()));
	        			if(count!= temp2.length-1){
	        				retData.append(",");
	        			}	
	        		}
	        	}
	        	//retData.append(HttpRequest.pullData(URLRequest, rawTickerData));
	        	return retData;
	        }
	    /**
	     * This method returns the stock ratios requested by the user
	     * @param stockTicker is the ticker symbol for a stock
	     * @param ratios is a string of characters where each character corresponds to a ratio
	     * @return retData is a string buffer that contains all the ratio information 
	     */    
	    public static StringBuffer getTechnicalRatios(String ticker, String name, String ratios){
	    	Logger.Log.Write("Pulling technical ratios for: " + ticker);
	    	String yahooBaseURL = "http://finance.yahoo.com/d/quotes.csv?s=";
	        String URLRequest = "";
	    	StringBuffer rawTickerData = new StringBuffer();
	        StringBuffer retData = new StringBuffer();
	        URLRequest += yahooBaseURL + ticker+"&f="+ratios;
	    	retData.append(name);
	    	retData.append(",");
	    	retData.append(ticker);
	    	retData.append(",");  
	        retData.append(Http.pullData(URLRequest, rawTickerData));
	        return retData;
	    }
	    

	    // Variables
	    static String base_url = "http://real-chart.finance.yahoo.com/table.csv?s=";
	    static String end_url = "g=d";

	    // http://real-chart.finance.yahoo.com/table.csv?s=AAPL&c=2013&a=00&b=01&d=11&e=31&f=2013&g=d
	    
	    // Gets the data from the database
	    public static StringBuffer GetHistoricalInformation(String start, String end, String t) throws CustomExceptions.InputException {
	      // Setup the dates
	      try {
	        SetDate(start, end);
	        CheckDate();
	      }
	      catch(CustomExceptions.InputException a) {
	        throw new CustomExceptions.InputException(a.getMessage());
	      }

	      // Set the ticker
	      SetTicker(t);

	      // Call the URL
	      StringBuffer string = new StringBuffer("");

	      try {
	        Logger.Log.Write(BuildURL());
	        string.append(Http.GetURLData(BuildURL()));
	        return string;
	      }
	      catch(CustomExceptions.NetworkError a) {
	        Logger.ErrorLog.Write("Failure 1 - Could not get the url: " + BuildURL());
	      }

	      try {
	        string.append(Http.GetURLData(BuildURL()));
	      }
	      catch(CustomExceptions.NetworkError a) {
	        Logger.ErrorLog.Write("Failure 2 - Could not get the url: " + BuildURL());
	      }

	      return string;
	    }

	    private static String BuildURL() {
	      StringBuilder url = new StringBuilder("");

	      // Break the start and end: YYYY-MM-DD
	      String [] splitStart = startDate.toString().split("-");
	      String [] splitEnd = endDate.toString().split("-");

	      // Add init
	      url.append(base_url);

	      // Add in ticker
	      url.append(ticker);
	      url.append("&");

	      // Add in start year
	      url.append("c=");
	      url.append(splitStart[0].toString());
	      url.append("&");

	      // Add in start month
	      url.append("a=");
	      if((Integer.parseInt(splitStart[1]) - 1) < 10) {
	        url.append("0");
	      }
	      url.append(Integer.parseInt(splitStart[1]) - 1);
	      url.append("&");

	      // Add in start day
	      url.append("b=");
	      url.append(splitStart[2].toString());
	      url.append("&");

	      // Add in end year
	      url.append("f=");
	      url.append(splitEnd[0].toString());
	      url.append("&");

	      // Add in end month
	      url.append("d=");
	      if((Integer.parseInt(splitEnd[1]) - 1) < 10) {
	        url.append("0");
	      }
	      url.append(Integer.parseInt(splitEnd[1]) - 1);
	      url.append("&");

	      // Add in end day
	      url.append("e=");
	      url.append(splitEnd[2].toString());
	      url.append("&");

	      // Add in the end part
	      url.append(end_url);

	      return url.toString();
	    }
	  }

	  private static void SetTicker(String t) {
	    ticker = new StringBuilder(t);
	  }

	  // Sets the date for the pull
	  private static void SetDate(String start, String end) throws CustomExceptions.InputException {
	    StringBuilder startDay, startMonth, startYear, endDay, endMonth, endYear;
	    startMonth = new StringBuilder("");
	    startDay = new StringBuilder("");
	    startYear = new StringBuilder("");
	    endMonth = new StringBuilder("");
	    endDay = new StringBuilder("");
	    endYear = new StringBuilder("");

	    SplitDate(start, startMonth, startDay, startYear);
	    SplitDate(end, endMonth, endDay, endYear);

	    try {
	      startDate = LocalDate.of(Integer.parseInt(startYear.toString()), Integer.parseInt(startMonth.toString()), Integer.parseInt(startDay.toString()));
	      endDate = LocalDate.of(Integer.parseInt(endYear.toString()), Integer.parseInt(endMonth.toString()), Integer.parseInt(endDay.toString()));
	    }
	    catch(DateTimeException e) {
	      throw new CustomExceptions.InputException(e.getMessage());
	    }

	    Logger.Log.Write("DateRange: " + startDate + " to " + endDate);
	  }

	  private static void CheckDate() throws CustomExceptions.InputException {
	    // Set the start date to a non-weekend value
	    if(startDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
	      startDate = startDate.plusDays(2);
	    }
	    else if(startDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
	      startDate = startDate.plusDays(1);
	    }
	    
	    // Will check for dates within the proper range
	    LocalDate initData;
	    initData = LocalDate.of(1960, 1, 1);

	    if(DateTimeIsLess(endDate, initData) || DateTimeIsEqual(endDate, LocalDate.now()) || DateTimeIsGreater(endDate, LocalDate.now())) {
	      throw new CustomExceptions.InputException("The end date is not within historical data range.");
	    }

	    if(DateTimeIsLess(startDate, initData) || DateTimeIsEqual(startDate, LocalDate.now()) || DateTimeIsGreater(startDate, LocalDate.now())) {
	      throw new CustomExceptions.InputException("The start date is not within historical data range.");
	    }

	    if(DateTimeIsGreater(startDate, endDate)) {
	      throw new CustomExceptions.InputException("The start date must be earlier than the end date.");
	    }
	  }

	  // Splits the date of the string passed
	  private static void SplitDate(String date, StringBuilder month, StringBuilder day, StringBuilder year) {
	    String [] newStart = date.split("-");

	    month.append(newStart[0]);
	    day.append(newStart[1]);
	    year.append(newStart[2]);
	  }

	  private String BuildSingleStockUrl(String baseURL, String StockTicker) {
	    return "http://ichart.finance.yahoo.com/table.csv?s=AAPL&c=2013&a=00&b=01&c=2013&d=11&e=31&f=2013&g=d";
	  }

	  private String BuildBatchUrls(String baseURL, String [] StockTickers) {
	    return "";
	  }

	  private static boolean DateTimeIsEqual(LocalDate one, LocalDate two) {
	    if(one.getYear() == two.getYear()) {
	      if(one.getDayOfYear() == two.getDayOfYear()) {
	        return true;
	      }
	    }
	    return false;
	  }

	  private static boolean DateTimeIsLess(LocalDate one, LocalDate two) {
	    if(one.getYear() < two.getYear()) {
	      return true;
	    }

	    if(one.getYear() == two.getYear()) {
	      if(one.getDayOfYear() < two.getDayOfYear()) {
	        return true;
	      }
	    }
	    return false;
	  }

	  private static boolean DateTimeIsGreater(LocalDate one, LocalDate two) {
	    if(one.getYear() > two.getYear()) {
	      return true;
	    }

	    if(one.getYear() == two.getYear()) {
	      if(one.getDayOfYear() > two.getDayOfYear()) {
	        return true;
	      }
	    }
	    return false;
	  }
}
