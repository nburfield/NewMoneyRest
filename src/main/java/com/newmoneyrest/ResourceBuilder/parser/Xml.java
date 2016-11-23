package com.newmoneyrest.ResourceBuilder.parser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Xml {
	public static Document GetDocumantFromString(StringBuilder text){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		ByteArrayInputStream input;
		Document doc = null;

		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			return doc;
		}
		try {
			input = new ByteArrayInputStream(text.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return doc;
		}
		try {
			doc = builder.parse(input);
		} catch (SAXException | IOException e) {
			return doc;
		}
		return doc;
	}


	public static Document GetDocumentFromFile(String fileName){
		Document doc = null;
		try {
			File xmlFle = new File(fileName);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();	
			doc = dBuilder.parse(xmlFle);
			doc.getDocumentElement().normalize();
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return doc;
	}
}
