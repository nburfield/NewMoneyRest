package com.newmoneyrest.ResourceBuilder.NetworkRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import com.newmoneyrest.ResourceBuilder.error.CustomExceptions;

public class Http {
	  // A test query for the data
	  public static String GetURLData(String url) throws CustomExceptions.NetworkError {
	    InputStream is;
	    BufferedReader rd;

	    try {
	      is = new URL(url).openStream();
	    }
	    catch(Exception e)
	    {
	      throw new CustomExceptions.NetworkError("Error Connecting to URL: " + e.getMessage());
	    }
	    try {
	      rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	    }
	    catch(Exception e)
	    {
	      throw new CustomExceptions.NetworkError("Error Reading the buffer: " + e.getMessage());
	    }
	    try {
	      String text = readAll(rd);
	      return text;
	    }
	    catch(Exception e)
	    {
	      throw new CustomExceptions.NetworkError("Error getting data from the buffer: " + e.getMessage());
	    }
	  }

	  private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

		public static StringBuffer pullData(String URLReq, StringBuffer buffReader) {
			try {
				InputStream incomingStream = new URL(URLReq).openStream();
				InputStreamReader inputStreamReader = new InputStreamReader(incomingStream, Charset.forName("UTF-8"));
				BufferedReader reader = new BufferedReader(inputStreamReader);
				int t;
				while((t = reader.read() )!= -1){
					buffReader.append((char)t);
				}
				buffReader.toString();
			}
			catch(Exception e) {
				System.out.println("Exception when pulling data: ");
				e.printStackTrace();
			}

			return buffReader;
		}


		public static String convertMonth(String month) {
			int mon = -1;
			
			String tMonth = month.toLowerCase();
			//	System.out.println(month);
			String month2 = tMonth.substring(0, 3);
			//	System.out.println(month2);

			switch(month2) {
			case "jan":
				mon += 1;
				break;
			case "feb":
				mon += 2;
				break;
			case "mar":
				mon += 3;
				break;
			case "apr":
				mon += 4;
				break;
			case "may":
				mon += 5;
				break;
			case "jun":
				mon += 6;
				break;
			case "jul":
				mon += 7;
				break;
			case "aug":
				mon += 8;
				break;
			case "sep":
				mon += 9;
				break;
			case "oct":
				mon += 10;
				break;
			case "nov":
				mon += 11;
				break;
			case "dec":
				mon += 12;
				break;
			default: 
				break;
			}

		  return Integer.toString(mon);
		}
}
