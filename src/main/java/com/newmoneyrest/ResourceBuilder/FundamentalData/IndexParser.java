package com.newmoneyrest.ResourceBuilder.FundamentalData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class IndexParser {
	
	public static Map<String, List<DailyData>> ParseDaily(StringBuilder file) {
		// Setup a scanner to iterate line by line
		Scanner scan = new Scanner(file.toString());
		// Move to the location in the file with the content
		String value = new String("");
		while(scan.hasNextLine() && !value.endsWith("--") && !value.startsWith("--")) {
			value = scan.nextLine();
		}
		// Extract content from the file
		Map<String, List<DailyData>> fileContents = new HashMap<String, List<DailyData>>();
		while(scan.hasNextLine()) {
			StringBuilder line = new StringBuilder(scan.nextLine());
			int last = 0;
			String [] list = new String[5];
			String form;
			StringBuilder temp = new StringBuilder(line.substring(0, line.indexOf("|", last)));
			while(temp.length() < 10) {
				temp.insert(0, '0');
			}
			list[0] = temp.toString();
			last = line.indexOf("|", last) + 1;
			list[1] = line.substring(last, line.indexOf("|", last));
			last = line.indexOf("|", last) + 1;
			list[2] = line.substring(last, line.indexOf("|", last));
			form = list[2];
			form = form.replace(' ', '_');
			form = form.replace('/', '_');
			form = form.replace('-', '_');
			last = line.indexOf("|", last) + 1;
			list[3] = line.substring(last, line.indexOf("|", last));
			last = line.indexOf("|", last) + 1;
			list[4] = line.substring(last, line.length());
			if(!fileContents.containsKey(form)) {
				fileContents.put(form, new ArrayList<DailyData>());
			}
			fileContents.get(form).add(new DailyData(list));
		}
		scan.close();
				
		// Return
		return fileContents;
	}
}
