package com.newmoneyrest.ResourceBuilder.error;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.APPEND;
import java.util.HashMap;
import java.util.Map;

import com.newmoneyrest.ResourceBuilder.profiler.Timer;
import com.newmoneyrest.ResourceBuilder.system.Directory;

public class Logger {
	  /**
	   * Writes the data to the given file name.
	   * @param filename: This is the open file to write to.
	   * @param data: This is the string to write to the file.
	   */
	  public static void WriteFile(File filename, String data) {
		  Path p = filename.toPath();
		  byte dataBytes[] = data.getBytes();
		  try (OutputStream f = new BufferedOutputStream(Files.newOutputStream(p, CREATE, APPEND))){
			  f.write(dataBytes, 0, dataBytes.length);
			  byte endl[] = "\n".getBytes();
			  f.write(endl, 0, endl.length);
		  }catch(IOException e) {
			  System.out.println("Error Writing to the Logger: " + e);
		  }
	  }
	    
	  
	  public static class ErrorLog {
		  // Variables for path and file names
		  private static final String errorPath = "ErrorLogs";
		  private static final String mainLog = "MainLog.txt";
		  
		  public static boolean MPI = false;
		  public static int rank;
		  
		  /**
		   * Writes to the main log of the error log.
		   * @param fileName: The name of the file to append to the main log.
		   */
		  private static void WriteMainLog(String fileName) {
			  File tempFile = new File(errorPath+"/"+mainLog);
			  WriteFile(tempFile, fileName);
		  }
		  
		  /**
		   * Gets the calling class name.
		   * @return Gives a string of the calling class.
		   */
		  public static String getCallerClassName() { 
		      StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		      for (int i=1; i<stElements.length; i++) {
		          StackTraceElement ste = stElements[i];
		          if (!ste.getClassName().equals(ErrorLog.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
		        	  StringBuilder name = new StringBuilder(ste.getClassName());
		              return name.substring(name.indexOf(".")+1, name.length());
		          }
		      }
		      return "General";
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
		  
		  /**
		   * Checks the main log of the error directory for existence.
		   * @return Exists or does not exist.
		   */
		  public static boolean checkMainLog(){
			  File file = new File(errorPath+"/"+mainLog); 
			  return file.length() == 0;
		  }
		  
		  /**
		   * Writes the data to the ErrorLog directory in a file named after the calling class.
		   * @param data: The data to write to file.
		   */
		  public static void Write(String data) {
			  if(MPI) {
				  Map<String, String> info = getCallerInfo();
				  info.put("data", data);
				  info.put("rank", String.valueOf(rank));
				  System.err.println(info);
			  }
			  else {
				  File tempFile = new File(errorPath + "/" + getCallerClassName() + ".txt");
				  Directory.create(errorPath);
				  WriteFile(tempFile, data);
				  WriteMainLog(tempFile.getName());
			  }
		  }
		  
		  public static void InitMPI(int r) {
			  rank = r;
			  MPI = true;
		  }
	  }

	  public static class Log {
		  // Variables for path and file names
		  private static final String errorPath = "Logs";
		  private static final String mainLog = "MainLog.txt";
		  
		  public static boolean MPI = false;
		  public static int rank;
		  
		  /**
		   * Writes to the main log of the log.
		   * @param fileName: The name of the file to append to the main log.
		   */
		  private static void WriteMainLog(String fileName) {
			  File tempFile = new File(errorPath+"/"+mainLog);
			  WriteFile(tempFile, fileName);
		  }
		  
		  /**
		   * Gets the calling class name.
		   * @return Gives a string of the calling class.
		   */
		  public static String getCallerClassName() { 
		      StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		      for (int i=1; i<stElements.length; i++) {
		          StackTraceElement ste = stElements[i];
		          if (!ste.getClassName().equals(Log.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
		        	  StringBuilder name = new StringBuilder(ste.getClassName());
		              return name.substring(name.indexOf(".")+1, name.length());
		          }
		      }
		      return "General";
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
		  
		  /**
		   * Checks the main log of the log directory for existence.
		   * @return Exists or does not exist.
		   */
		  public static boolean checkMainLog(){
			  File file = new File(errorPath+"/"+mainLog); 
			  return file.length() == 0;
		  }
		  
		  /**
		   * Writes the data to the Log directory in a file named after the calling class.
		   * @param data: The data to write to file.
		   */
		  public static void Write(String stuff) {
			  if(MPI) {
				  Map<String, String> info = getCallerInfo();
				  info.put("data", stuff);
				  info.put("rank", String.valueOf(rank));
				  System.out.println(info);
			  }
			  else {
				  File tempFile = new File(errorPath + "/" + getCallerClassName() + ".txt");
				  Directory.create(errorPath);
				  WriteFile(tempFile, stuff);
				  WriteMainLog(tempFile.getName());
			  }
		  }
		  
		  public static void InitMPI(int r) {
			  rank = r;
			  MPI = true;
		  }
	  }
}
