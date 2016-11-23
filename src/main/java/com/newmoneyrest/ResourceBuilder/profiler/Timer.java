package com.newmoneyrest.ResourceBuilder.profiler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newmoneyrest.ResourceBuilder.error.Logger;

public class Timer {
	public static boolean MPI = false;
	public static int rank;
	private static List<String> dump = new ArrayList<String>();
	
	public static long start(){
		return System.nanoTime(); 
	}
	
	public static long stop(){
		
		return System.nanoTime();

	}
	
	public static void initMPI(int r) {
		MPI = true;
		rank = r;
	}
	
	public static long getElapsedTime(long start, long stop){
		return (stop - start);
	}
	
	public static void writeResults(long timeValue){
		File tempFile = new File("Logs" + "/" + "timings" + ".txt");
		Map<String, String> info = getCallerInfo();
		info.put("time", String.valueOf(timeValue));
		if(MPI) {
			Logger.WriteFile( tempFile, "Rank " + rank + ": " + info.toString());
		}
		else {
			Logger.WriteFile( tempFile, info.toString());	
		}
	}
	
	public static void printResults(long timeValue){
		Map<String, String> info = getCallerInfo();
		info.put("time", String.valueOf(timeValue));
		
		if(MPI) {
			dump.add("Rank " + rank + ": " + info.toString());
		}
		else {
			System.out.println(info.toString());	
		}
	}
	
	public static void dump() {
		for(String d : dump) {
			System.out.println(d);
		}
	}
	
	public static Map<String, String> getCallerInfo() { 
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		Map<String, String> info = new HashMap<String, String>();
		
		for (int i=1; i<stElements.length; i++) {
			StackTraceElement ste = stElements[i];

			if (!ste.getClassName().equals(Timer.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
				StringBuilder name = new StringBuilder(ste.getClassName());
				info.put("class", name.substring(name.indexOf(".")+1, name.length()));
				info.put("function", ste.getMethodName());
				return info;
			}
		}
		info.put("class", "General");
		info.put("function", "General");
		return info;
	}
}
