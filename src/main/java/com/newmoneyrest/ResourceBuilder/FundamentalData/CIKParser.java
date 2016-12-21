package com.newmoneyrest.ResourceBuilder.FundamentalData;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.newmoneyrest.ResourceBuilder.parser.Csv;

public class CIKParser extends Csv {
	
	private BufferedReader child_reader;
	private Map<String, String >cikTicker = new HashMap<String, String>();
	private Map<String, String > tickerName = new HashMap<String, String>();	
	private Map<String, String >cikName = new HashMap<String, String>();		
	
	public CIKParser(String fileName) {
		super(fileName);
		ParseCIKFile();
	}
	public String getFileName() {
		return super.getFileName();
	}
	private void ParseCIKFile(){
		child_reader = super.returnParsedFileReader(child_reader);
		try{
			String readLine = " "; 
			String [] brokenLine;
			while((readLine = child_reader.readLine()) != null){
				brokenLine = readLine.split(",");
				tickerName.put(brokenLine[1], brokenLine[2]);
				cikTicker.put(brokenLine[0], brokenLine[1]);
				cikName.put(brokenLine[0], brokenLine[2]);
			}
		}
		catch(IOException e){
			System.out.println("IOException thrown by BufferReader.");
			e.printStackTrace();
		}
		try{
			child_reader.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public Map<String, String> returnParsedValuesbyCIKTickers(){
		return cikName;
	}
	public Map<String, String> returnParsedValuesbyCIKName(){
		return cikTicker;
	}	
	public Map<String, String> returnParsedValuesbyTickerNames(){
		return tickerName;
	}
	
	public void printCIKNames(){
		printMapKeyValues(cikName);
	}
	public void printCIKTickers(){
		printMapKeyValues(cikTicker);		
	}
	public void printTickerNames(){
		printMapKeyValues(tickerName);		
	}
	private void printMapKeyValues(Map<String, String> map){
		for (String key : map.keySet()){
			System.out.println(key+ " " + map.get(key));
		}
	}
}
