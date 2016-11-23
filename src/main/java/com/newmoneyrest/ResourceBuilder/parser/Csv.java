package com.newmoneyrest.ResourceBuilder.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Csv {
	// Variables
	private String fname;
	
	// Default Constructor
	public Csv() {	

	}

	// Constructor with a given file
	public Csv(String fileName) {
		setFileName(fileName);
	}

	// Setting the name of the file to open
	public void setFileName(String fileName) {
		fname = fileName;
	}

	// Return the name of the file
	public String getFileName() {
		return fname;
	}

	// return parsed file
	public BufferedReader returnParsedFileReader(BufferedReader read) {
		return extractDataFromFile(read);
	}

	// Extract data from reader
	private BufferedReader extractDataFromFile(BufferedReader reader) {
		// Run a try on opening the file
		try {
			File fn = new File(fname);
			FileReader fileReader = new FileReader(fn);
			reader = new BufferedReader(fileReader);
		}
		catch (NullPointerException e) {
			System.out.println("Pathname argument is Null. IOException: NullPointerException thrown.");
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found. "+ e +":FileNotFoundException thrown.");
		}

		return reader;
	}
}
